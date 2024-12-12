package be.kdg.prog6.PlayerManagementContext.domain;

public record FriendRequest(
        PlayerId senderId,
        FriendRequestStatus friendRequestStatus
) {
}
