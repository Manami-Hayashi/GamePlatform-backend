package be.kdg.PlayerManagementContext.domain;

public class Friend {
    private PlayerId friendId;
    private boolean isFavorite;

    public Friend(PlayerId friendId, boolean isFavorite) {
        this.friendId = friendId;
        this.isFavorite = isFavorite;
    }

}
