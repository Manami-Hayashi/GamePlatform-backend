package be.kdg.prog6.PlayerManagementContext.domain;

public class Friend {
    private final PlayerId friendId;
    private String name;
    private boolean isFavorite;
    private Player player;

    public Friend(PlayerId friendId, boolean isFavorite) {
        this.friendId = friendId;
        this.isFavorite = isFavorite;
    }

    public Friend(PlayerId friendId, String name, boolean isFavorite) {
        this.friendId = friendId;
        this.name = name;
        this.isFavorite = isFavorite;
    }

    public Friend(PlayerId friendId, String name) {
        this.friendId = friendId;
        this.name = name;
        this.isFavorite = false;
    }

    public Friend(PlayerId friendId, String name, boolean isFavorite, Player player) {
        this.friendId = friendId;
        this.name = name;
        this.isFavorite = isFavorite;
        this.player = player;
    }

    public Friend(PlayerId friendId, String name, Player player) {
        this.friendId = friendId;
        this.name = name;
        this.isFavorite = false;
        this.player = player;
    }

    public Friend(PlayerId friendId, Player player) {
        this.friendId = friendId;
        this.player = player;
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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
