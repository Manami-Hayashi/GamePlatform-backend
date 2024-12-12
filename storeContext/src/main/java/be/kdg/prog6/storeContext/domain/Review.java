package be.kdg.prog6.storeContext.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Review {
    private UUID reviewId;
    private CustomerId customerId;
    private GameId gameId;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;


    public Review(UUID reviewId, CustomerId customerId, GameId gameId, int rating, String comment, LocalDateTime createdAt) {
        this.reviewId = reviewId;
        this.customerId = customerId;
        this.gameId = gameId;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public UUID getReviewId() {
        return reviewId;
    }

    public CustomerId getPlayerId() {
        return customerId;
    }

    public GameId getGameId() {return gameId;}

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setReviewId(UUID reviewId) {this.reviewId = reviewId;}

    public void setPlayerId(CustomerId customerId) {this.customerId = customerId;}

    public void setGameId(GameId gameId) {this.gameId = gameId;}

    public void setRating(int rating) {this.rating = rating;}

    public void setComment(String comment) {this.comment = comment;}

    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}
}
