package be.kdg.prog6.storeContext.domain;


import java.math.BigDecimal;
import java.util.List;

public class StoreGame {
    private GameId gameId;
    private String gameName;
    private BigDecimal price;
    private String description;
    private List<Review> reviews;

    public StoreGame(GameId gameId, String gameName, BigDecimal price, String description, List<Review> reviews) {
        this.gameId = gameId;
        this.gameName = gameName;
        this.price = price;
        this.description = description;
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

    public String getGameName() {
        return gameName;
    }

    public void setGameId(GameId gameId) {this.gameId = gameId;}

    public void setGameName(String gameName) {this.gameName = gameName;}

    public void setPrice(BigDecimal price) {this.price = price;}

    public void setReviews(List<Review> reviews) {this.reviews = reviews;}

    public void addReview(Review review) {
        reviews.add(review);
    }

    public void removeReview(Review review) {
        reviews.remove(review);
    }

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}
}
