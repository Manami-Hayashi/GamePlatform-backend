package be.kdg.prog6.lobbyManagementContext.core;

import be.kdg.prog6.lobbyManagementContext.domain.Game;
import be.kdg.prog6.lobbyManagementContext.domain.GameId;
import be.kdg.prog6.lobbyManagementContext.domain.Lobby;
import be.kdg.prog6.lobbyManagementContext.domain.Player;
import be.kdg.prog6.lobbyManagementContext.domain.PlayerId;
import be.kdg.prog6.lobbyManagementContext.ports.in.MatchPlayersCommand;
import be.kdg.prog6.lobbyManagementContext.ports.in.MatchWithFriendUseCase;
import be.kdg.prog6.lobbyManagementContext.ports.out.LoadPlayerPort;
import be.kdg.prog6.lobbyManagementContext.ports.out.SaveLobbyPort;
import be.kdg.prog6.lobbyManagementContext.ports.out.UpdatePlayerPort;
import be.kdg.prog6.lobbyManagementContext.ports.out.LoadLobbyGamePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.UUID;

@Service
public class MatchWithFriendUseCaseImpl implements MatchWithFriendUseCase {
    private final LoadPlayerPort loadPlayerPort;
    private final SaveLobbyPort saveLobbyPort;
    private final UpdatePlayerPort updatePlayerPort;
    private final LoadLobbyGamePort loadLobbyGamePort;

    private static final Logger LOGGER = LoggerFactory.getLogger(MatchWithFriendUseCaseImpl.class);

    public MatchWithFriendUseCaseImpl(LoadPlayerPort loadPlayerPort, SaveLobbyPort saveLobbyPort, UpdatePlayerPort updatePlayerPort, LoadLobbyGamePort loadLobbyGamePort) {
        this.loadPlayerPort = loadPlayerPort;
        this.saveLobbyPort = saveLobbyPort;
        this.updatePlayerPort = updatePlayerPort;
        this.loadLobbyGamePort = loadLobbyGamePort;
    }

    @Transactional
    @Override
    public void matchPlayers(MatchPlayersCommand command) {
        LOGGER.info("Matching player {} with friend {}", command.getPlayerId(), command.getFriendId());

        // Load and verify player
        Player player = loadAndVerifyPlayer(command.getPlayerId());
        Player friend = loadAndVerifyPlayer(command.getFriendId());

        // Load game details
        Game game = loadLobbyGamePort.loadLobbyGame(command.getGameId());
        if (game == null) {
            LOGGER.error("Game with ID {} not found", command.getGameId());
            throw new IllegalStateException("Game not found.");
        }
        LOGGER.info("Game {} loaded", game.getGameName());

        GameId gameId = new GameId(command.getGameId());
        // Create a private lobby and invite the friend
        Lobby privateLobby = createNewLobby(player.getPlayerId(), gameId);
        privateLobby.addPlayer(friend.getPlayerId());

        player.setGameId(gameId);
        LOGGER.info("game id {} is set for the player {}",player.getGameId(), player.getPlayerId());
        friend.setGameId(gameId);
        LOGGER.info("game id {} is set for the player {}",friend.getGameId(), friend.getPlayerId());

        player.setLobbyId(privateLobby.getLobbyId());
        friend.setLobbyId(privateLobby.getLobbyId());

        saveLobbyPort.saveLobby(privateLobby);
        LOGGER.info("Private lobby created for player {} and friend {}, lobby saved", player.getPlayerId(), friend.getPlayerId());

        // Update activity for both players
        player.updateActivity();
        friend.updateActivity();
        updatePlayerPort.updatePlayer(player);
        updatePlayerPort.updatePlayer(friend);
    }

    // Helper method to load and verify player
    private Player loadAndVerifyPlayer(UUID playerId) {
        Player player = loadPlayerPort.loadPlayer(playerId);
        if (player == null || !player.isOnline()) {
            LOGGER.error("Player {} is either offline or does not exist", playerId);
            throw new IllegalStateException("Player is not online or does not exist.");
        }
        return player;
    }

    // Create a new lobby and add the player
    private Lobby createNewLobby(PlayerId playerId, GameId gameId) {
        Lobby lobby = new Lobby(gameId);
        lobby.addPlayer(playerId);
        saveLobbyPort.saveLobby(lobby);
        return lobby;
    }
}