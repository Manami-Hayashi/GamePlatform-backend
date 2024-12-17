package be.kdg.prog6.playerManagementContext.core;

import be.kdg.prog6.playerManagementContext.domain.Friend;
import be.kdg.prog6.playerManagementContext.domain.FriendRequestStatus;
import be.kdg.prog6.playerManagementContext.domain.Player;
import be.kdg.prog6.playerManagementContext.domain.PlayerId;
import be.kdg.prog6.playerManagementContext.ports.in.SendFriendRequestUseCase;
import be.kdg.prog6.playerManagementContext.ports.out.LoadPlayerPort;
import be.kdg.prog6.playerManagementContext.ports.out.UpdatePlayerPort;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class SendFriendRequestUseCaseImpl implements SendFriendRequestUseCase {
    private final LoadPlayerPort loadPlayerPort;
    private final UpdatePlayerPort updatePlayerPort;

    public SendFriendRequestUseCaseImpl(LoadPlayerPort loadPlayerPort, @Qualifier("playerDbAdapter") UpdatePlayerPort updatePlayerPort) {
        this.loadPlayerPort = loadPlayerPort;
        this.updatePlayerPort = updatePlayerPort;
    }

    @Override
    public void sendFriendRequest(PlayerId senderId, PlayerId accepterId) {
        // Load sender and accepter players
        Player sender = loadPlayerPort.loadPlayer(senderId.id());
        Player accepter = loadPlayerPort.loadPlayer(accepterId.id());

        // Validate that sender and accepter are not the same
        if (senderId.equals(accepterId)) {
            throw new IllegalArgumentException("A player cannot send a friend request to themselves.");
        }

        // Check if a friend request already exists, or they are already friends
        for (Friend friend : sender.getFriendsInitiated()) {
            if (friend.getReceiver().getPlayerId().equals(accepterId)) {
                throw new IllegalArgumentException("Friend request already sent.");
            }
        }
        for (Friend friend : sender.getFriendsReceived()) {
            if (friend.getRequester().getPlayerId().equals(accepterId)) {
                throw new IllegalArgumentException("Friend request already received.");
            }
        }
        for (Friend friend : sender.getFriends()) {
            if (friend.getRequester().getPlayerId().equals(accepterId) || friend.getReceiver().getPlayerId().equals(accepterId)) {
                throw new IllegalArgumentException("Players are already friends.");
            }
        }

        // Create a new Friend object
        Friend friendRequest = new Friend(sender, accepter);
        friendRequest.setFriendRequestStatus(FriendRequestStatus.REQUESTED);

        // Update sender and accepter relationships
        sender.addFriendInitiated(friendRequest);
        accepter.addFriendReceived(friendRequest);

        // Save updated players
        updatePlayerPort.updatePlayer(sender);
        updatePlayerPort.updatePlayer(accepter);
    }
}