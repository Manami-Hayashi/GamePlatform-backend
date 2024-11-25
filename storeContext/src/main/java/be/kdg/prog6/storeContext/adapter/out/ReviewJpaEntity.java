package be.kdg.prog6.storeContext.adapter.out;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(catalog="store", name="reviews")
public class ReviewJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID reviewId;

    @Column(name="player_id")
    private UUID playerId;

//    @Column(name="game_id")
//    private UUID gameId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", nullable = false)
    private StoreGameJpaEntity game;

    @Column(name="rating")
    private int rating;

    @Column(name="comment")
    private String comment;

    @Column(name="created_at")
    private LocalDateTime createdAt;



    public ReviewJpaEntity(UUID reviewId, UUID playerId, StoreGameJpaEntity game , int rating, String comment, LocalDateTime createdAt) {
        this.reviewId = reviewId;
        this.playerId = playerId;
        this.game = game;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public ReviewJpaEntity() {}


    public UUID getReviewId() {return reviewId;}

    public void setReviewId(UUID reviewId) {this.reviewId = reviewId;}

    public UUID getPlayerId() {return playerId;}

    public void setPlayerId(UUID playerId) {this.playerId = playerId;}

    public StoreGameJpaEntity getGame() {return game;}

    public void setGame(StoreGameJpaEntity game) {this.game = game;}

    public UUID getGameId() {return game.getGameId();}

    public void setGameId(UUID gameId) {game.setGameId(gameId);}

    public int getRating() {return rating;}

    public void setRating(int rating) {this.rating = rating;}

    public String getComment() {return comment;}

    public void setComment(String comment) {this.comment = comment;}

    public LocalDateTime getCreatedAt() {return createdAt;}

    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}


}

