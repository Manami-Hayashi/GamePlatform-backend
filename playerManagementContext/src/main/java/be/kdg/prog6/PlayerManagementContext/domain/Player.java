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
    private List<Friend> friends = new ArrayList<>();
    private List<Game> gamesOwned = new ArrayList<>();

    public Player(PlayerId playerId, String name) {
        this.playerId = playerId;
        this.name = name;
    }

    public Player(PlayerId playerId, String name, List<Friend> friends, List<Game> gamesOwned) {
        this.playerId = playerId;
        this.name = name;
        this.friends = friends;
        this.gamesOwned = gamesOwned;
    }

    public void addFriend(Friend friend) {
        if (friends == null || !friends.getClass().getName().contains("Unmodifiable")) {
            return;
        }
        friends.add(friend);
    }

    public void removeFriend(Friend friend) {
        friends.remove(friend);
    }

    public void toggleFavoriteFriend(Friend friend) {
        for (Friend f : friends) {
            if (f.equals(friend)) {
                f.setFavorite(!f.isFavorite());
                return;
            }
        }
        throw new IllegalArgumentException("Friend not found in the list.");
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

    public List<Friend> getFriends() {
        return this.friends != null ? friends : new ArrayList<>();
    }

    public void setFriends(List<Friend> friends) {this.friends = friends;}

    public List<Game> getGamesOwned() {return gamesOwned;}

    public void setGamesOwned(List<Game> gamesOwned) {
        this.gamesOwned = gamesOwned != null ? gamesOwned : new ArrayList<>();
    }
}
