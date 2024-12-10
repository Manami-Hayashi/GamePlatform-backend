package be.kdg.prog6.PlayerManagementContext.core;

import be.kdg.prog6.PlayerManagementContext.domain.Friend;
import be.kdg.prog6.PlayerManagementContext.domain.FriendRequestStatus;
import be.kdg.prog6.PlayerManagementContext.domain.Player;
import be.kdg.prog6.PlayerManagementContext.domain.PlayerId;
import be.kdg.prog6.PlayerManagementContext.port.in.SendFriendRequestUseCase;
import be.kdg.prog6.PlayerManagementContext.port.out.LoadPlayerPort;
import be.kdg.prog6.PlayerManagementContext.port.out.UpdatePlayerPort;
import org.springframework.stereotype.Service;

@Service
public class SendFriendRequestUseCaseImpl implements SendFriendRequestUseCase {
    private final LoadPlayerPort loadPlayerPort;
    private final UpdatePlayerPort updatePlayerPort;

    public SendFriendRequestUseCaseImpl(LoadPlayerPort loadPlayerPort, UpdatePlayerPort updatePlayerPort) {
        this.loadPlayerPort = loadPlayerPort;
        this.updatePlayerPort = updatePlayerPort;
    }

    @Override
    public void sendFriendRequest(PlayerId senderId, PlayerId accepterId) {
        Player sender = loadPlayerPort.loadPlayer(senderId.id());
        Player accepter = loadPlayerPort.loadPlayer(accepterId.id());

        // ...

        updatePlayerPort.updatePlayer(sender);
        updatePlayerPort.updatePlayer(accepter);
    }
}
