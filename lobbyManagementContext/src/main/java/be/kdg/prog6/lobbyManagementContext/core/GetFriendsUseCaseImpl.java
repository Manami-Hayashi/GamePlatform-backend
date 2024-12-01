package be.kdg.prog6.lobbyManagementContext.core;

import be.kdg.prog6.lobbyManagementContext.domain.Player;
import be.kdg.prog6.lobbyManagementContext.ports.in.GetFriendsUseCase;
import be.kdg.prog6.lobbyManagementContext.ports.out.LoadAllPlayersPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetFriendsUseCaseImpl implements GetFriendsUseCase {
    private final LoadAllPlayersPort loadAllPlayersPort;

    public GetFriendsUseCaseImpl(LoadAllPlayersPort loadAllPlayersPort) {
        this.loadAllPlayersPort = loadAllPlayersPort;
    }

    @Override
    public List<Player> getFriends() {
        List<Player> allPlayers = loadAllPlayersPort.loadAllPlayers();
        return allPlayers.stream()
                .filter(player -> player.getLobbyId() == null)
                .collect(Collectors.toList());
    }
}