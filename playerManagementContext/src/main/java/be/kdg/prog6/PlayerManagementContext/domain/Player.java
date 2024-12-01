package be.kdg.prog6.PlayerManagementContext.domain;

import java.time.LocalDate;
import java.util.List;

public class Player {
    private PlayerId playerId;
    private String name;

    private List<Friend> friends;
    private List<Game> gamesOwned;

    public Player(PlayerId playerId, String name) {
        this.playerId = playerId;
        this.name = name;
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
        for (Game ownedGame : gamesOwned) {
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
        int age = LocalDate.now().getYear() - birthDate.getYear();
        return age;
    }

    public PlayerId getPlayerId() {return playerId;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}


    public List<Friend> getFriends() {return friends;}

    public void setFriends(List<Friend> friends) {this.friends = friends;}

    public List<Game> getGamesOwned() {return gamesOwned;}

    public void setGamesOwned(List<Game> gamesOwned) {this.gamesOwned = gamesOwned;}

}
