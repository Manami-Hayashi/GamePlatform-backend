package be.kdg.prog6.PlayerManagementContext.adapter.in;

public class FriendDto {
    private final String playerId;
    private final String name;
    private final boolean isFavorite;

    public FriendDto(String playerId, String name, boolean isFavorite) {
        this.playerId = playerId;
        this.name = name;
        this.isFavorite = isFavorite;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getName() {
        return name;
    }

    public boolean isFavorite() {
        return isFavorite;
    }
}
