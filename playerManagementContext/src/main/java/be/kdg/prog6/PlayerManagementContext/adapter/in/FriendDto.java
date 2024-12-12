package be.kdg.prog6.PlayerManagementContext.adapter.in;

public record FriendDto(
        String senderId,
        String senderName,
        String receiverId,
        String receiverName,
        String friendRequestStatus) {
}
