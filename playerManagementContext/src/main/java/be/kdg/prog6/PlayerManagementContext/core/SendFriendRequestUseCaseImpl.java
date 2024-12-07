package be.kdg.prog6.PlayerManagementContext.core;

import be.kdg.prog6.PlayerManagementContext.domain.Friend;
import be.kdg.prog6.PlayerManagementContext.domain.FriendRequestStatus;
import be.kdg.prog6.PlayerManagementContext.domain.Player;
import be.kdg.prog6.PlayerManagementContext.domain.PlayerId;
import be.kdg.prog6.PlayerManagementContext.port.in.SendFriendRequestUseCase;
import be.kdg.prog6.PlayerManagementContext.port.out.CreateFriendPort;
import be.kdg.prog6.PlayerManagementContext.port.out.PlayerLoadedPort;
import be.kdg.prog6.PlayerManagementContext.port.out.UpdatePlayerPort;
import org.springframework.stereotype.Service;

@Service
public class SendFriendRequestUseCaseImpl implements SendFriendRequestUseCase {
    private final PlayerLoadedPort playerLoadedPort;
    private final UpdatePlayerPort updatePlayerPort;
    private final CreateFriendPort createFriendPort;

    public SendFriendRequestUseCaseImpl(PlayerLoadedPort playerLoadedPort, UpdatePlayerPort updatePlayerPort, CreateFriendPort createFriendPort) {
        this.playerLoadedPort = playerLoadedPort;
        this.updatePlayerPort = updatePlayerPort;
        this.createFriendPort = createFriendPort;
    }

    @Override
    public void sendFriendRequest(PlayerId senderId, PlayerId accepterId) {
        Player sender = playerLoadedPort.loadPlayer(senderId.id());
        Player accepter = playerLoadedPort.loadPlayer(accepterId.id());

        Friend accepterFriend = new Friend(accepter.getPlayerId(), accepter.getName(), FriendRequestStatus.SENT, sender);
        createFriendPort.createFriend(accepterFriend, sender);
        sender.addFriend(accepterFriend);
        updatePlayerPort.updatePlayer(sender);

        Friend senderFriend = new Friend(sender.getPlayerId(), sender.getName(), FriendRequestStatus.TO_RESPOND, accepter);
        createFriendPort.createFriend(senderFriend, accepter);
        accepter.addFriend(senderFriend);
        updatePlayerPort.updatePlayer(accepter);

    }
}
