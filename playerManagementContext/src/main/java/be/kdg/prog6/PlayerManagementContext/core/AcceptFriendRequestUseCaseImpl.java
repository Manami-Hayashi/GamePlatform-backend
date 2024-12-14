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
    public void acceptFriendRequest(PlayerId requesterId, PlayerId receiverId) {
        // Load requester and receiver players
        Player requester = loadPlayerPort.loadPlayer(requesterId.id());
        Player receiver = loadPlayerPort.loadPlayer(receiverId.id());
        LOGGER.info("Loaded requester with ID {} and receiver with ID {}", requesterId, receiverId);

        // Validate that requester and receiver are not the same
        if (requesterId.equals(receiverId)) {
            throw new IllegalArgumentException("A player cannot accept their own friend request.");
        }

        // Find the friend request in the receiver's received list
        Friend friendRequest = null;
        for (Friend friend : receiver.getFriendsReceived()) {
            if (friend.getRequester().getPlayerId().id().equals(requesterId.id()) &&
                    friend.getFriendRequestStatus().equals(FriendRequestStatus.REQUESTED)) {
                friendRequest = friend;
                break;
            }
        }

        if (friendRequest == null) {
            throw new IllegalArgumentException("No pending friend request found from the specified requester.");
        }

        // Update the friend request status
        friendRequest.setFriendRequestStatus(FriendRequestStatus.ACCEPTED);

        // Persist the updated requester and receiver
        updatePlayerPort.updatePlayer(requester);
        updatePlayerPort.updatePlayer(receiver);

        // Log the successful acceptance
        LOGGER.info("Friend request from Player {} to Player {} has been accepted.", requesterId, receiverId);


    }
}
