package be.kdg.prog6.storeContext.domain;


import java.math.BigDecimal;
import java.util.List;

public class StoreGame {
    private GameId gameId;
    private String name;
    private BigDecimal price;
    private List<Review> reviews;

    public StoreGame(GameId gameId, String name, BigDecimal price, List<Review> reviews) {
        this.gameId = gameId;
        this.name = name;
        this.price = price;
        this.reviews = reviews;
    }

    public StoreGame() {}


    public GameId getGameId() {
        return gameId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public String getName() {
        return name;
    }

    public void setGameId(GameId gameId) {this.gameId = gameId;}

    public void setName(String name) {this.name = name;}

    public void setPrice(BigDecimal price) {this.price = price;}

    public void setReviews(List<Review> reviews) {this.reviews = reviews;}

    public void addReview(Review review) {
        reviews.add(review);
    }

    public void removeReview(Review review) {
        reviews.remove(review);
    }
}
