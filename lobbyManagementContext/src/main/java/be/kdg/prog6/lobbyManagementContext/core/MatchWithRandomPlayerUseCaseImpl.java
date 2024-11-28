package be.kdg.prog6.lobbyManagementContext.core;

import be.kdg.prog6.lobbyManagementContext.domain.Game;
import be.kdg.prog6.lobbyManagementContext.domain.GameId;
import be.kdg.prog6.lobbyManagementContext.domain.Lobby;
import be.kdg.prog6.lobbyManagementContext.domain.Player;
import be.kdg.prog6.lobbyManagementContext.domain.PlayerId;
import be.kdg.prog6.lobbyManagementContext.ports.in.MatchPlayersCommand;
import be.kdg.prog6.lobbyManagementContext.ports.in.MatchWithRandomPlayerUseCase;
import be.kdg.prog6.lobbyManagementContext.ports.out.LoadAllLobbiesPort;
import be.kdg.prog6.lobbyManagementContext.ports.out.LoadLobbyGamePort;
import be.kdg.prog6.lobbyManagementContext.ports.out.LoadPlayerPort;
import be.kdg.prog6.lobbyManagementContext.ports.out.SaveLobbyPort;
import be.kdg.prog6.lobbyManagementContext.ports.out.UpdatePlayerPort;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MatchWithRandomPlayerUseCaseImpl implements MatchWithRandomPlayerUseCase {
    private final LoadAllLobbiesPort loadAllLobbiesPort;
    private final SaveLobbyPort saveLobbyPort;
    private final LoadPlayerPort loadPlayerPort;
    private final UpdatePlayerPort updatePlayerPort;
    private final LoadLobbyGamePort loadLobbyGamePort;

    private static final Logger LOGGER = LoggerFactory.getLogger(MatchWithRandomPlayerUseCaseImpl.class);

    public MatchWithRandomPlayerUseCaseImpl(LoadAllLobbiesPort loadAllLobbiesPort, SaveLobbyPort saveLobbyPort,
                                            LoadPlayerPort loadPlayerPort, UpdatePlayerPort updatePlayerPort,
                                            LoadLobbyGamePort loadLobbyGamePort) {
        this.loadAllLobbiesPort = loadAllLobbiesPort;
        this.saveLobbyPort = saveLobbyPort;
        this.loadPlayerPort = loadPlayerPort;
        this.updatePlayerPort = updatePlayerPort;
        this.loadLobbyGamePort = loadLobbyGamePort;
    }

    @Transactional
    @Override
    public void matchPlayers(MatchPlayersCommand command) {
        LOGGER.info("Matching player {} with random players for game {}", command.getPlayerId(), command.getGameId());

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

        // Load lobbies for the specific game where all players are online
        List<Lobby> lobbies = loadAllLobbiesPort.loadAllLobbies().stream()
                .filter(lobby -> lobby.getGameId().id().compareTo(command.getGameId()) == 0)
                .filter(lobby -> lobby.getPlayerIds().stream()
                        .allMatch(playerId -> loadPlayerPort.loadPlayer(playerId.id()).isOnline()))
                .toList();

        LOGGER.info("Loaded {} lobbies with all players online for game {}", lobbies.size(), command.getGameId());

        GameId gameId = new GameId(command.getGameId());
        // Find a lobby with space or create a new one
        Lobby playerLobby = lobbies.stream()
                .filter(lobby -> !lobby.isFull())
                .findFirst()
                .orElseGet(() -> {
                    LOGGER.info("No existing lobby with space found, creating a new lobby");
                    return createNewLobby(player.getPlayerId(), gameId);
                });

        // Add player to the selected lobby
        if (!playerLobby.containsPlayer(player.getPlayerId())) {
            playerLobby.addPlayer(player.getPlayerId());
        }

        player.setLobbyId(playerLobby.getLobbyId());
        saveLobbyPort.saveLobby(playerLobby);
        LOGGER.info("Player {} matched with random player and lobby saved", player.getPlayerId());

        // Update activity for the player
        player.updateActivity();
        updatePlayerPort.updatePlayer(player);
    }

    // Helper method to load and verify player is online
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