package be.kdg.prog6.PlayerManagementContext.domain;

public class Friend {
    private final PlayerId friendId;
    private String name;
    private boolean isFavorite;
    private FriendRequestStatus friendRequestStatus;
    private Player player;

    public Friend(PlayerId friendId, boolean isFavorite) {
        this.friendId = friendId;
        this.isFavorite = isFavorite;
        this.friendRequestStatus = FriendRequestStatus.NONE;
    }

    public Friend(PlayerId friendId, String name, boolean isFavorite) {
        this.friendId = friendId;
        this.name = name;
        this.isFavorite = isFavorite;
        this.friendRequestStatus = FriendRequestStatus.NONE;
    }

    public Friend(PlayerId friendId, String name) {
        this.friendId = friendId;
        this.name = name;
        this.isFavorite = false;
        this.friendRequestStatus = FriendRequestStatus.NONE;
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

    public Friend(PlayerId friendId, String name, FriendRequestStatus friendRequestStatus) {
        this.friendId = friendId;
        this.name = name;
        this.friendRequestStatus = friendRequestStatus;
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

    public FriendRequestStatus getFriendRequestStatus() {
        return friendRequestStatus;
    }

    public void setFriendRequestStatus(FriendRequestStatus friendRequestStatus) {
        this.friendRequestStatus = friendRequestStatus;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
