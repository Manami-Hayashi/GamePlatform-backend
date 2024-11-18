package be.kdg.PlayerManagementContext.domain;

import java.time.LocalDate;
import java.util.List;

public class Player {
    private PlayerId playerId;
    private String name;
    private String location;
    private LocalDate birthDate;
    private List<Friend> friends;
    private List<Game> gamesOwned;

    public Player(PlayerId playerId, String name, String location, LocalDate birthDate) {
        this.playerId = playerId;
        this.name = name;
        this.location = location;
        this.birthDate = birthDate;
    }

    public void addFriend(Friend friend) {
        friends.add(friend);
    }

    public void removeFriend(Friend friend) {
        friends.remove(friend);
    }

    public void toggleFavoriteFriend(Friend friend) {

    }

    public void toggleFavoriteGame(Game game) {

    }

    public void selectGame(Game game) {

    }

    public int getAge(LocalDate birthDate){
        int age = LocalDate.now().getYear() - birthDate.getYear();
        return age;
    }

    public PlayerId getPlayerId() {return playerId;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getLocation() {return location;}

    public void setLocation(String location) {this.location = location;}

    public LocalDate getBirthDate() {return birthDate;}

    public void setBirthDate(LocalDate birthDate) {this.birthDate = birthDate;}

    public List<Friend> getFriends() {return friends;}

    public void setFriends(List<Friend> friends) {this.friends = friends;}

    public List<Game> getGamesOwned() {return gamesOwned;}

    public void setGamesOwned(List<Game> gamesOwned) {this.gamesOwned = gamesOwned;}

}
