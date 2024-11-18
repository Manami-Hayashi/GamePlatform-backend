package be.kdg.PlayerManagementContext.domain;

import java.time.LocalDate;
import java.util.List;

public class Player {
    private PlayerId playerId;
    private String name;
    private String location;
    private LocalDate birthDate;
    private List<Friend> friends;

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




}
