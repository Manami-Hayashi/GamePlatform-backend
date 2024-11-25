package be.kdg.prog6.storeContext.adapter.in;

import be.kdg.prog6.storeContext.domain.Review;
import be.kdg.prog6.storeContext.domain.StoreGame;

import java.math.BigDecimal;
import java.util.List;

public class StoreGameDto {
    private String gameId;
    private String name;
    private BigDecimal price;
    private String description;
    private List<Review> reviews;

    public StoreGameDto(String string, String name, BigDecimal price,
                        String description, List<Review> reviews) {
        this.gameId = string;
        this.name = name;
        this.price = price;
        this.description = description;
        this.reviews = reviews;

    }

    public StoreGameDto(StoreGame storeGame) {
        this(
            storeGame.getGameId().id().toString(),
            storeGame.getName(),
            storeGame.getPrice(),
            storeGame.getDescription(),
            storeGame.getReviews()
        );

    }

    public String getGameId() {return gameId;}

    public void setGameId(String gameId) {this.gameId = gameId;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public BigDecimal getPrice() {return price;}

    public void setPrice(BigDecimal price) {this.price = price;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

    public List<Review> getReviews() {return reviews;}

    public void setReviews(List<Review> reviews) {this.reviews = reviews;}
}
