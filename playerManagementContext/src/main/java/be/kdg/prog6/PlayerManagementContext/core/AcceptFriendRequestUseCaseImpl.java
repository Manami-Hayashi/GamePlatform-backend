package be.kdg.prog6.PlayerManagementContext.core;

import be.kdg.prog6.PlayerManagementContext.domain.Friend;
import be.kdg.prog6.PlayerManagementContext.domain.FriendRequestStatus;
import be.kdg.prog6.PlayerManagementContext.domain.Player;
import be.kdg.prog6.PlayerManagementContext.domain.PlayerId;
import be.kdg.prog6.PlayerManagementContext.port.in.AcceptFriendRequestUseCase;
import be.kdg.prog6.PlayerManagementContext.port.out.PlayerLoadedPort;
import be.kdg.prog6.PlayerManagementContext.port.out.UpdateFriendPort;
import org.springframework.stereotype.Service;

@Service
public class AcceptFriendRequestUseCaseImpl implements AcceptFriendRequestUseCase {
    private final PlayerLoadedPort playerLoadedPort;
    private final UpdateFriendPort updateFriendPort;

    public AcceptFriendRequestUseCaseImpl(PlayerLoadedPort playerLoadedPort, UpdateFriendPort updateFriendPort) {
        this.playerLoadedPort = playerLoadedPort;
        this.updateFriendPort = updateFriendPort;
    }

    @Override
    public void acceptFriendRequest(PlayerId senderId, PlayerId accepterId) {
        Player sender = playerLoadedPort.loadPlayer(senderId.id());
        Player accepter = playerLoadedPort.loadPlayer(accepterId.id());

        Friend senderFriend = sender.getFriends().stream()
                .filter(friend -> friend.getFriendId().equals(senderId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Friend not found"));
        senderFriend.setFriendRequestStatus(FriendRequestStatus.ACCEPTED);
        updateFriendPort.updateFriend(senderFriend, accepterId.id());

        Friend accepterFriend = accepter.getFriends().stream()
                .filter(friend -> friend.getFriendId().equals(accepterId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Friend not found"));
        accepterFriend.setFriendRequestStatus(FriendRequestStatus.ACCEPTED);
        updateFriendPort.updateFriend(accepterFriend, senderId.id());
    }
}
