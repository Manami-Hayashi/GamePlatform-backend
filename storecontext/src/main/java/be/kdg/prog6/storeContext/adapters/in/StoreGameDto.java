package be.kdg.prog6.storeContext.adapters.in;

import be.kdg.prog6.storeContext.domain.Review;
import be.kdg.prog6.storeContext.domain.StoreGame;

import java.math.BigDecimal;
import java.util.List;

public class StoreGameDto {
    private String gameId;
    private String gameName;
    private BigDecimal price;
    private String description;
    private List<Review> reviews;

    public StoreGameDto(String string, String gameName, BigDecimal price,
                        String description, List<Review> reviews) {
        this.gameId = string;
        this.gameName = gameName;
        this.price = price;
        this.description = description;
        this.reviews = reviews;

    }

    public StoreGameDto(StoreGame storeGame) {
        this(
            storeGame.getGameId().id().toString(),
            storeGame.getGameName(),
            storeGame.getPrice(),
            storeGame.getDescription(),
            storeGame.getReviews()
        );

    }

    public String getGameId() {return gameId;}

    public void setGameId(String gameId) {this.gameId = gameId;}

    public String getGameName() {return gameName;}

    public void setGameName(String gameName) {this.gameName = gameName;}

    public BigDecimal getPrice() {return price;}

    public void setPrice(BigDecimal price) {this.price = price;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

    public List<Review> getReviews() {return reviews;}

    public void setReviews(List<Review> reviews) {this.reviews = reviews;}
}
