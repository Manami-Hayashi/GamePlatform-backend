package be.kdg.prog6.playerManagementContext.domain;

public class Friend {
    private Player requester;
    private Player receiver;
    private FriendRequestStatus friendRequestStatus;

    public Friend(Player requester, Player receiver) {
        this.requester = requester;
        this.receiver = receiver;
        this.friendRequestStatus = FriendRequestStatus.NONE;
    }

    public Friend(Player requester, Player receiver, FriendRequestStatus friendRequestStatus) {
        this.requester = requester;
        this.receiver = receiver;
        this.friendRequestStatus = friendRequestStatus;
    }

    public Friend(FriendRequestStatus friendRequestStatus) {
        this.friendRequestStatus = friendRequestStatus;
    }

    public Player getRequester() {
        return requester;
    }

    public void setRequester(Player player1) {
        this.requester = player1;
    }

    public Player getReceiver() {
        return receiver;
    }

    public void setReceiver(Player player2) {
        this.receiver = player2;
    }

    public FriendRequestStatus getFriendRequestStatus() {
        return friendRequestStatus;
    }

    public void setFriendRequestStatus(FriendRequestStatus friendRequestStatus) {
        this.friendRequestStatus = friendRequestStatus;
    }
}
