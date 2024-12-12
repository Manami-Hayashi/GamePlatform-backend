package be.kdg.prog6.PlayerManagementContext.core;

import be.kdg.prog6.PlayerManagementContext.domain.Friend;
import be.kdg.prog6.PlayerManagementContext.domain.FriendRequestStatus;
import be.kdg.prog6.PlayerManagementContext.domain.Player;
import be.kdg.prog6.PlayerManagementContext.domain.PlayerId;
import be.kdg.prog6.PlayerManagementContext.port.in.AcceptFriendRequestUseCase;
import be.kdg.prog6.PlayerManagementContext.port.out.LoadPlayerPort;

import be.kdg.prog6.PlayerManagementContext.port.out.UpdatePlayerPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AcceptFriendRequestUseCaseImpl implements AcceptFriendRequestUseCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(AcceptFriendRequestUseCaseImpl.class);
    private final LoadPlayerPort loadPlayerPort;
    private final UpdatePlayerPort updatePlayerPort;

    public AcceptFriendRequestUseCaseImpl(LoadPlayerPort loadPlayerPort, UpdatePlayerPort updatePlayerPort) {
        this.loadPlayerPort = loadPlayerPort;
        this.updatePlayerPort = updatePlayerPort;
    }

    @Override
    public void acceptFriendRequest(PlayerId senderId, PlayerId accepterId) {
        // Load sender and accepter players
        Player sender = loadPlayerPort.loadPlayer(senderId.id());
        Player accepter = loadPlayerPort.loadPlayer(accepterId.id());

        // Validate that sender and accepter are not the same
        if (senderId.equals(accepterId)) {
            throw new IllegalArgumentException("A player cannot accept their own friend request.");
        }

        // Find the friend request in the accepter's received list
        Friend friendRequest = null;
        for (Friend friend : accepter.getFriendsReceived()) {
            if (friend.getPlayer1().getPlayerId().equals(senderId) &&
                    friend.getFriendRequestStatus().equals(FriendRequestStatus.REQUESTED)) {
                friendRequest = friend;
                break;
            }
        }

        if (friendRequest == null) {
            throw new IllegalArgumentException("No pending friend request found from the specified sender.");
        }

        // Update the friend request status
        friendRequest.setFriendRequestStatus(FriendRequestStatus.ACCEPTED);

        // Persist the updated sender and accepter
        updatePlayerPort.updatePlayer(sender);
        updatePlayerPort.updatePlayer(accepter);

        // Log the successful acceptance
        LOGGER.info("Friend request from Player {} to Player {} has been accepted.", senderId, accepterId);
    }
}
