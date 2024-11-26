package be.kdg.prog6.lobbyManagementContext.core;

import be.kdg.prog6.lobbyManagementContext.domain.Lobby;
import be.kdg.prog6.lobbyManagementContext.domain.Player;
import be.kdg.prog6.lobbyManagementContext.domain.PlayerId;
import be.kdg.prog6.lobbyManagementContext.ports.in.MatchPlayersCommand;
import be.kdg.prog6.lobbyManagementContext.ports.in.MatchPlayersUseCase;
import be.kdg.prog6.lobbyManagementContext.ports.out.LoadAllLobbiesPort;
import be.kdg.prog6.lobbyManagementContext.ports.out.LoadPlayerPort;
import be.kdg.prog6.lobbyManagementContext.ports.out.SaveLobbyPort;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MatchPlayersUseCaseImpl implements MatchPlayersUseCase {
    private final LoadAllLobbiesPort loadAllLobbiesPort;
    private final SaveLobbyPort saveLobbyPort;
    private final LoadPlayerPort loadPlayerPort;

    private static final Logger LOGGER = LoggerFactory.getLogger(MatchPlayersUseCaseImpl.class);

    public MatchPlayersUseCaseImpl(LoadAllLobbiesPort loadAllLobbiesPort, SaveLobbyPort saveLobbyPort, LoadPlayerPort loadPlayerPort) {
        this.loadAllLobbiesPort = loadAllLobbiesPort;
        this.saveLobbyPort = saveLobbyPort;
        this.loadPlayerPort = loadPlayerPort;
    }

    @Override
    @Transactional
    public void matchPlayers(MatchPlayersCommand command) {
        LOGGER.info("Matching players for player ID: {}", command.playerId());

        // Load and verify player
        Player player = loadAndVerifyPlayer(command.playerId());
        LOGGER.info("Player {} loaded and verified", player.getPlayerId());

        Player friend = command.friendId() != null ? loadAndVerifyPlayer(command.friendId()) : null;
        if (friend != null) {
            LOGGER.info("Matching player {} with friend {}", player.getPlayerId(), friend.getPlayerId());
        } else {
            LOGGER.info("Matching player {} with a random player", player.getPlayerId());
        }

        // Load all lobbies
        List<Lobby> lobbies = loadAllLobbiesPort.loadAllLobbies();
        LOGGER.info("Loaded {} lobbies", lobbies.size());

        // Find or create a lobby for the player
        Lobby playerLobby = lobbies.stream()
                .filter(lobby -> lobby.containsPlayer(player.getPlayerId()))
                .findFirst()
                .orElseGet(() -> {
                    LOGGER.info("No existing lobby found for player {}, creating new lobby", player.getPlayerId());
                    return createNewLobby(player.getPlayerId());
                });

        // Invite a friend or match with a random player
        if (friend != null) {
            LOGGER.info("Inviting friend {} to lobby", friend.getPlayerId());
            playerLobby.inviteFriend(player.getPlayerId(), friend.getPlayerId());
        } else {
            LOGGER.info("Matching player {} with random players", player.getPlayerId());
            playerLobby.matchWithRandomPlayer(fetchAvailablePlayers(playerLobby));
        }

        // Save the updated lobby
        saveLobbyPort.saveLobby(playerLobby);
        LOGGER.info("Lobby for player {} saved successfully", player.getPlayerId());
    }

    // Helper method to load and verify player is online
    private Player loadAndVerifyPlayer(UUID playerId) {
        LOGGER.debug("Loading player with ID: {}", playerId);
        Player player = loadPlayerPort.loadPlayer(playerId);
        if (player == null || !player.isOnline()) {
            LOGGER.error("Player {} is either offline or does not exist", playerId);
            throw new IllegalStateException("Player is not online or does not exist.");
        }
        LOGGER.debug("Player {} is online, updating activity", playerId);
        player.updateActivity();
        return player;
    }

    // Create a new lobby and add the player
    private Lobby createNewLobby(PlayerId playerId) {
        LOGGER.debug("Creating new lobby for player {}", playerId);
        Lobby lobby = new Lobby();
        lobby.addPlayer(playerId);
        saveLobbyPort.saveLobby(lobby);
        LOGGER.info("New lobby created and saved for player {}", playerId);
        return lobby;
    }

    // Fetch available players that are not in the current lobby
    private List<PlayerId> fetchAvailablePlayers(Lobby currentLobby) {
        LOGGER.debug("Fetching available players for matching from lobbies");
        return loadAllLobbiesPort.loadAllLobbies().stream()
                .flatMap(lobby -> lobby.getPlayerIds().stream())
                .filter(playerId -> !currentLobby.containsPlayer(playerId))
                .collect(Collectors.toList());
    }
}
