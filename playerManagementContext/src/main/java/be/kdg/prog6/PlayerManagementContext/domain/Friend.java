package be.kdg.prog6.PlayerManagementContext.domain;

public class Friend {
    private final PlayerId friendId;
    private boolean isFavorite;

    public Friend(PlayerId friendId, boolean isFavorite) {
        this.friendId = friendId;
        this.isFavorite = isFavorite;
    }

    public PlayerId getFriendId() {
        return friendId;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

}
