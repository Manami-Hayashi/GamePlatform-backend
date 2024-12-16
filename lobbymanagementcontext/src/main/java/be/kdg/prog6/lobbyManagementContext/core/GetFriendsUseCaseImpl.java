// GetFriendsUseCaseImpl.java
package be.kdg.prog6.lobbyManagementContext.core;

import be.kdg.prog6.lobbyManagementContext.domain.Player;
import be.kdg.prog6.lobbyManagementContext.ports.in.GetFriendsUseCase;
import be.kdg.prog6.lobbyManagementContext.ports.out.LoadPlayerPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GetFriendsUseCaseImpl implements GetFriendsUseCase {
    private final LoadPlayerPort loadPlayerPort;

    public GetFriendsUseCaseImpl(LoadPlayerPort loadPlayerPort) {
        this.loadPlayerPort = loadPlayerPort;
    }

    @Override
    @Transactional
    public List<Player> getFriends(UUID playerId) {
        Player player = loadPlayerPort.loadPlayer(playerId);
        return player.getFriends().stream()
                .map(friendId -> loadPlayerPort.loadPlayer(friendId.id()))
                .collect(Collectors.toList());
    }
}