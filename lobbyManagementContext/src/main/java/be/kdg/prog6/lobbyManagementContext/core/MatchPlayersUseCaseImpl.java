package be.kdg.prog6.lobbyManagementContext.core;

import be.kdg.prog6.lobbyManagementContext.domain.Lobby;
import be.kdg.prog6.lobbyManagementContext.domain.PlayerId;
import be.kdg.prog6.lobbyManagementContext.ports.in.MatchPlayersCommand;
import be.kdg.prog6.lobbyManagementContext.ports.in.MatchPlayersUseCase;
import be.kdg.prog6.lobbyManagementContext.ports.out.LoadAllLobbiesPort;
import be.kdg.prog6.lobbyManagementContext.ports.out.SaveLobbyPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MatchPlayersUseCaseImpl implements MatchPlayersUseCase {
    private final LoadAllLobbiesPort loadAllLobbiesPort;
    private final SaveLobbyPort saveLobbyPort;

    public MatchPlayersUseCaseImpl(LoadAllLobbiesPort loadAllLobbiesPort, SaveLobbyPort saveLobbyPort) {
        this.loadAllLobbiesPort = loadAllLobbiesPort;
        this.saveLobbyPort = saveLobbyPort;
    }

    @Override
    @Transactional
    public void matchPlayers(MatchPlayersCommand command) {
        UUID playerId = command.playerId();
        UUID friendId = command.friendId();

        List<Lobby> lobbies = loadAllLobbiesPort.loadAllLobbies();
        List<PlayerId> allPlayers = lobbies.stream()
                .flatMap(lobby -> lobby.getPlayers().stream())
                .toList();

        boolean playerInLobby = allPlayers.contains(new PlayerId(playerId));
        boolean friendInLobby = allPlayers.contains(new PlayerId(friendId));

        if (!playerInLobby && !friendInLobby) {
            // Both players are not in any lobby, create a new lobby and add both players
            Lobby newLobby = new Lobby();
            newLobby.addPlayer(new PlayerId(playerId));
            newLobby.addPlayer(new PlayerId(friendId));
            saveLobbyPort.saveLobby(newLobby);
        } else {
            // Existing logic for adding players to an available lobby
            Lobby availableLobby = lobbies.stream()
                    .filter(lobby -> !lobby.isFull())
                    .findFirst()
                    .orElse(null);

            if (availableLobby == null) {
                availableLobby = new Lobby();
                availableLobby.addPlayer(new PlayerId(playerId));
                saveLobbyPort.saveLobby(availableLobby);
                // Wait for another player to join
                return;
            }

            if (friendId != null) {
                availableLobby.inviteFriend(new PlayerId(playerId), new PlayerId(friendId));
            } else {
                List<PlayerId> existingPlayers = lobbies.stream()
                        .flatMap(lobby -> lobby.getPlayers().stream())
                        .filter(player -> !player.equals(new PlayerId(playerId)))
                        .collect(Collectors.toList());
                availableLobby.matchWithRandomPlayer(new PlayerId(playerId), existingPlayers);
            }

            saveLobbyPort.saveLobby(availableLobby);
        }
    }
}