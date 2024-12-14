package be.kdg.prog6.lobbyManagementContext.core;

import be.kdg.prog6.lobbyManagementContext.domain.Player;
import be.kdg.prog6.lobbyManagementContext.ports.in.AddFriendUseCase;
import be.kdg.prog6.lobbyManagementContext.ports.out.LoadPlayerPort;
import be.kdg.prog6.lobbyManagementContext.ports.out.UpdatePlayerPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AddFriendUseCaseImpl implements AddFriendUseCase {
    private final LoadPlayerPort loadPlayerPort;
    private final UpdatePlayerPort updatePlayerPort;

    public AddFriendUseCaseImpl(LoadPlayerPort loadPlayerPort, UpdatePlayerPort updatePlayerPort) {
        this.loadPlayerPort = loadPlayerPort;
        this.updatePlayerPort = updatePlayerPort;
    }

    @Override
    @Transactional
    public void addFriend(UUID playerId, UUID friendId) {
        Player player = loadPlayerPort.loadPlayer(playerId);
        Player friend = loadPlayerPort.loadPlayer(friendId);

        player.addFriend(friend.getPlayerId());
        friend.addFriend(player.getPlayerId());
        updatePlayerPort.updatePlayer(player);
        updatePlayerPort.updatePlayer(friend);
    }
}
