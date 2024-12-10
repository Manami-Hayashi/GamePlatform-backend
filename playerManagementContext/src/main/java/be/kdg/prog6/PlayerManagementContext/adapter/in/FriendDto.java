package be.kdg.prog6.PlayerManagementContext.adapter.in;

public class FriendDto {
    private final String playerId;
    private final String name;

    public FriendDto(String playerId, String name) {
        this.playerId = playerId;
        this.name = name;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getName() {
        return name;
    }
}
