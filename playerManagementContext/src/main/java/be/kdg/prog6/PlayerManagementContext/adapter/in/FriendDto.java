package be.kdg.prog6.PlayerManagementContext.adapter.in;

public class FriendDto {
    private final String playerId;
    private final String name;
    private final String friendRequestStatus;

    public FriendDto(String playerId, String name, String friendRequestStatus) {
        this.playerId = playerId;
        this.name = name;
        this.friendRequestStatus = friendRequestStatus;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getName() {
        return name;
    }

    public String getFriendRequestStatus() {
        return friendRequestStatus;
    }
}
