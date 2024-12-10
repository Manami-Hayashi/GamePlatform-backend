package be.kdg.prog6.PlayerManagementContext.domain;

public class Friend {
    private Player player1;
    private Player player2;
    private FriendRequestStatus friendRequestStatus;

    public Friend() {
        this.friendRequestStatus = FriendRequestStatus.NONE;
    }

    public Friend(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.friendRequestStatus = FriendRequestStatus.NONE;
    }

    public Friend(FriendRequestStatus friendRequestStatus) {
        this.friendRequestStatus = friendRequestStatus;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public FriendRequestStatus getFriendRequestStatus() {
        return friendRequestStatus;
    }

    public void setFriendRequestStatus(FriendRequestStatus friendRequestStatus) {
        this.friendRequestStatus = friendRequestStatus;
    }
}
