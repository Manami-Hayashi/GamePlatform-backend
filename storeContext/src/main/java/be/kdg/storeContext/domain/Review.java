package be.kdg.storeContext.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Review {
    private UUID reviewId;
    private PlayerId playerId;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;

    public Review(UUID reviewId, PlayerId playerId, int rating, String comment, LocalDateTime createdAt) {
        this.reviewId = reviewId;
        this.playerId = playerId;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
    }


    public UUID getReviewId() {
        return reviewId;
    }

    public PlayerId getPlayerId() {
        return playerId;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
