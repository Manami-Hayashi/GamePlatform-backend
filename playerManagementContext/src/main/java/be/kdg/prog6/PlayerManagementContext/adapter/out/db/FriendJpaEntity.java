package be.kdg.prog6.PlayerManagementContext.adapter.out.db;

import jakarta.persistence.*;

@Entity
@Table(catalog="player_management", name="friends")
public class FriendJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "friend_request_status")
    private String friendRequestStatus;

    @ManyToOne
    @JoinColumn(name = "requester", nullable = false)
    private PlayerJpaEntity requester;

    @ManyToOne
    @JoinColumn(name = "receiver", nullable = false)
    private PlayerJpaEntity receiver;

    public FriendJpaEntity() {
    }

    public FriendJpaEntity(String friendRequestStatus) {
        this.friendRequestStatus = friendRequestStatus;
    }

    public FriendJpaEntity(String friendRequestStatus, PlayerJpaEntity requester, PlayerJpaEntity receiver) {
        this.friendRequestStatus = friendRequestStatus;
        this.requester = requester;
        this.receiver = receiver;
    }

    public int getId() {
        return id;
    }

    public String getFriendRequestStatus() {
        return friendRequestStatus;
    }

    public void setFriendRequestStatus(String accepted) {
        friendRequestStatus = accepted;
    }

    public PlayerJpaEntity getRequester() {
        return requester;
    }

    public PlayerJpaEntity getReceiver() {
        return receiver;
    }

    public void setRequester(PlayerJpaEntity requester) {
        this.requester = requester;
    }

    public void setReceiver(PlayerJpaEntity receiver) {
        this.receiver = receiver;
    }
}
