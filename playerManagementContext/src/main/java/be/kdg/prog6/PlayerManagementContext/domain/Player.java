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
    private List<Game> gamesOwned = new ArrayList<>();

    public Player(PlayerId playerId, String name) {
        this.playerId = playerId;
        this.name = name;
    }

    public Player(PlayerId playerId, String name, List<Friend> friendsInitiated, List<Friend> friendsReceived, List<Game> gamesOwned) {
        this.playerId = playerId;
        this.name = name;
        this.friendsInitiated = friendsInitiated;
        this.friendsReceived = friendsReceived;
        this.gamesOwned = gamesOwned;
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

    }

    public int getAge(LocalDate birthDate){
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    public PlayerId getPlayerId() {return playerId;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public List<Friend> getFriendsInitiated() {return friendsInitiated;}

    public void setFriendsInitiated(List<Friend> friendsInitiated) {
        this.friendsInitiated = friendsInitiated != null ? friendsInitiated : new ArrayList<>();
    }

    public List<Friend> getFriendsReceived() {return friendsReceived;}

    public void setFriendsReceived(List<Friend> friendsReceived) {
        this.friendsReceived = friendsReceived != null ? friendsReceived : new ArrayList<>();
    }

    public void addFriendInitiated(Friend friend) {
        friendsInitiated.add(friend);
    }

    public void addFriendReceived(Friend friend) {
        friendsReceived.add(friend);
    }

    public List<Game> getGamesOwned() {return gamesOwned;}

    public void setGamesOwned(List<Game> gamesOwned) {
        this.gamesOwned = gamesOwned != null ? gamesOwned : new ArrayList<>();
    }

    public List<Friend> getFriends(){
        List<Friend> friends = new ArrayList<>();
        for (Friend friend : friendsInitiated) {
            if (friend.getFriendRequestStatus().equals(FriendRequestStatus.ACCEPTED)) {
                friends.add(friend);
            }
        }
        for (Friend friend : friendsReceived) {
            if (friend.getFriendRequestStatus().equals(FriendRequestStatus.ACCEPTED)) {
                friends.add(friend);
            }
        }
        return friends;
    }
}
