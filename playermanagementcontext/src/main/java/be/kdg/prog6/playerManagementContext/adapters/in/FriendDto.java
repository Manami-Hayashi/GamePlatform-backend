package be.kdg.prog6.playerManagementContext.adapters.in;

public record FriendDto(
        String senderId,
        String senderName,
        String receiverId,
        String receiverName,
        String friendRequestStatus) {
}
