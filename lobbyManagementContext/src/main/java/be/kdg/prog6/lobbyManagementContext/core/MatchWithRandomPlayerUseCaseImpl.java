package be.kdg.prog6.lobbyManagementContext.core;

import be.kdg.prog6.lobbyManagementContext.domain.*;
import be.kdg.prog6.lobbyManagementContext.ports.in.MatchPlayersCommand;
import be.kdg.prog6.lobbyManagementContext.ports.in.MatchWithRandomPlayerUseCase;
import be.kdg.prog6.lobbyManagementContext.ports.out.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MatchWithRandomPlayerUseCaseImpl implements MatchWithRandomPlayerUseCase {
    private final LoadPlayerPort loadPlayerPort;
    private final SaveLobbyPort saveLobbyPort;
    private final UpdatePlayerPort updatePlayerPort;
    private final LoadAllLobbiesPort loadAllLobbiesPort;
    private final LoadLobbyGamePort loadLobbyGamePort;

    private static final Logger LOGGER = LoggerFactory.getLogger(MatchWithRandomPlayerUseCaseImpl.class);

    public MatchWithRandomPlayerUseCaseImpl(LoadPlayerPort loadPlayerPort, SaveLobbyPort saveLobbyPort, UpdatePlayerPort updatePlayerPort, LoadAllLobbiesPort loadAllLobbiesPort, LoadLobbyGamePort loadLobbyGamePort) {
        this.loadPlayerPort = loadPlayerPort;
        this.saveLobbyPort = saveLobbyPort;
        this.updatePlayerPort = updatePlayerPort;
        this.loadAllLobbiesPort = loadAllLobbiesPort;
        this.loadLobbyGamePort = loadLobbyGamePort;
    }

    @Transactional
    @Override
    public void matchPlayers(MatchPlayersCommand command) {
        LOGGER.info("Matching player {} with a random player", command.getPlayerId());

        // Load and verify player
        Player player = loadAndVerifyPlayer(command.getPlayerId());
        LOGGER.info("Player {} loaded and verified", player.getPlayerId());

        // Load game details
        Game game = loadLobbyGamePort.loadLobbyGame(command.getGameId());
        if (game == null) {
            LOGGER.error("Game with ID {} not found", command.getGameId());
            throw new IllegalStateException("Game not found.");
        }
        LOGGER.info("Game {} loaded", game.getGameName());

        // Set the game ID for the player
        player.setGameId(new GameId(command.getGameId()));
        LOGGER.info("game id {} is set for the player {}",player.getGameId(), player.getPlayerId());

        // Load lobbies for the specific game where all players are online
        List<Lobby> lobbies = loadAllLobbiesPort.loadAllLobbies().stream()
                .filter(lobby -> game.getGameId().id().equals(command.getGameId()))
                .filter(lobby -> lobby.getPlayerIds().stream().allMatch(playerId -> {
                    Player p = loadPlayerPort.loadPlayer(playerId.id());
                    return p.isOnline() && p.getGameId().equals(new GameId(command.getGameId()));
                }))
                .toList();


        LOGGER.info("Loaded {} lobbies for game {}", lobbies.size(), game.getGameName());


        // Try to find a lobby that is not full
        for (Lobby lobby : lobbies) {
            if (!lobby.isFull()) {
                lobby.addPlayer(player.getPlayerId());
                if (lobby.getGameId() == null) {
                    lobby.setGameId(new GameId(command.getGameId()));
                }

                player.setLobbyId(lobby.getLobbyId());

                saveLobbyPort.saveLobby(lobby);
                updatePlayerPort.updatePlayer(player);
                LOGGER.info("Player {} added to existing lobby {}", player.getPlayerId(), lobby.getLobbyId());
                return;
            }
        }

        // If no suitable lobby is found, create a new one
        Lobby newLobby = new Lobby(new GameId(command.getGameId()));
        newLobby.addPlayer(player.getPlayerId());


        player.setLobbyId(newLobby.getLobbyId());
        saveLobbyPort.saveLobby(newLobby);
        updatePlayerPort.updatePlayer(player);
        LOGGER.info("New lobby created for player {}, lobby ID {}", player.getPlayerId(), newLobby.getLobbyId());
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
}