// Player.java
package be.kdg.prog6.PlayerManagementContext.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {
    private static final Logger LOGGER = LoggerFactory.getLogger(Player.class);
    private final PlayerId playerId;
    private String name;
    private List<Friend> friendsInitiated;
    private List<Friend> friendsReceived;
    private List<Game> gamesOwned;

    public Player(PlayerId playerId, String name) {
        this.playerId = playerId;
        this.name = name;
        this.friendsInitiated = new ArrayList<>();
        this.friendsReceived = new ArrayList<>();
        this.gamesOwned = new ArrayList<>();
    }

    public Player(PlayerId playerId, String name, List<Friend> friendsInitiated, List<Friend> friendsReceived, List<Game> gamesOwned) {
        this.playerId = playerId;
        this.name = name;
        this.friendsInitiated = new ArrayList<>(friendsInitiated);
        this.friendsReceived = new ArrayList<>(friendsReceived);
        this.gamesOwned = new ArrayList<>(gamesOwned);
    }

    public void toggleFavoriteGame(Game game) {
        for (Game ownedGame : gamesOwned) {
            LOGGER.info("Checking game with ID {}", ownedGame.getGameId());
            if (ownedGame.getGameId().equals(game.getGameId())) {
                ownedGame.setFavorite(!ownedGame.isFavorite());
                return;
            }
        }
        throw new IllegalArgumentException("Game is not owned by the player.");
    }

    public void selectGame(Game game) {
        // Implementation here
    }

    public int getAge(LocalDate birthDate){
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    public PlayerId getPlayerId() { return playerId; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public List<Friend> getFriendsInitiated() { return friendsInitiated; }

    public void setFriendsInitiated(List<Friend> friendsInitiated) {
        this.friendsInitiated = new ArrayList<>(friendsInitiated);
    }

    public List<Friend> getFriendsReceived() { return friendsReceived; }

    public void setFriendsReceived(List<Friend> friendsReceived) {
        this.friendsReceived = new ArrayList<>(friendsReceived);
    }

    public void addFriendInitiated(Friend friend) {
        friendsInitiated.add(friend);
    }

    public void addFriendReceived(Friend friend) {
        friendsReceived.add(friend);
    }

    public void addFriendsInitiated(List<Friend> friends) {
        if (friends == null) {
            throw new IllegalArgumentException("Friends list cannot be null.");
        }
        friendsInitiated.addAll(friends);
    }

    public void addFriendsReceived(List<Friend> friends) {
        if (friends == null) {
            throw new IllegalArgumentException("Friends list cannot be null.");
        }
        friendsReceived.addAll(friends);
    }

    public List<Game> getGamesOwned() { return gamesOwned; }

    public void setGamesOwned(List<Game> gamesOwned) {
        this.gamesOwned = new ArrayList<>(gamesOwned);
    }

    public List<Friend> getFriends(){
        List<Friend> friends = new ArrayList<>();
        friends.addAll(friendsInitiated);
        friends.addAll(friendsReceived);
        return friends;
    }

    public void initiateFriendRequest(Player recipient) {
        if (recipient == null) {
            throw new IllegalArgumentException("Recipient player cannot be null.");
        }
        Friend friendRequest = new Friend(this, recipient);
        friendsInitiated.add(friendRequest);
        recipient.friendsReceived.add(friendRequest);
        LOGGER.info("Friend request initiated from {} to {}", this.playerId, recipient.getPlayerId());
    }

    public void acceptFriendRequest(Friend friendRequest) {
        if (friendsReceived.contains(friendRequest)) {
            friendRequest.setFriendRequestStatus(FriendRequestStatus.ACCEPTED);
            LOGGER.info("Friend request accepted between {} and {}", friendRequest.getPlayer1().getPlayerId(), friendRequest.getPlayer2().getPlayerId());
        } else {
            throw new IllegalArgumentException("No such friend request exists.");
        }
    }

    public void declineFriendRequest(Friend friendRequest) {
        if (friendsReceived.contains(friendRequest)) {
            friendRequest.setFriendRequestStatus(FriendRequestStatus.NONE);
            LOGGER.info("Friend request declined between {} and {}", friendRequest.getPlayer1().getPlayerId(), friendRequest.getPlayer2().getPlayerId());
        } else {
            throw new IllegalArgumentException("No such friend request exists.");
        }
    }
}
