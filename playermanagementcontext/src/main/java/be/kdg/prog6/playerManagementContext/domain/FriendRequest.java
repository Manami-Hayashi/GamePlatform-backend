package be.kdg.prog6.playerManagementContext.domain;

public record FriendRequest(
        PlayerId senderId,
        FriendRequestStatus friendRequestStatus
) {
}
