package be.kdg.prog6.storeContext.domain;


import java.util.List;

public class StoreGame {
    private GameId gameId;
    private String name;
    private float price;
    private List<Review> reviews;

    public StoreGame(GameId gameId, String name, float price, List<Review> reviews) {
        this.gameId = gameId;
        this.name = name;
        this.price = price;
        this.reviews = reviews;
    }

    public GameId getGameId() {
        return gameId;
    }

    public float getPrice() {
        return price;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public String getName() {
        return name;
    }
}
