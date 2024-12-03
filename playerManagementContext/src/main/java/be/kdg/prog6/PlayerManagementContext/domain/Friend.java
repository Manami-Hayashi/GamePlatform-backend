package be.kdg.prog6.PlayerManagementContext.domain;

public class Friend {
    private final PlayerId friendId;
    private String name;
    private boolean isFavorite;

    public Friend(PlayerId friendId, boolean isFavorite) {
        this.friendId = friendId;
        this.isFavorite = isFavorite;
    }

    public Friend(PlayerId friendId, String name, boolean isFavorite) {
        this.friendId = friendId;
        this.name = name;
        this.isFavorite = isFavorite;
    }

    public PlayerId getFriendId() {
        return friendId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

}
