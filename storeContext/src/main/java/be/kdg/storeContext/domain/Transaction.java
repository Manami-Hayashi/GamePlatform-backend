package be.kdg.storeContext.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {
    private UUID transactionId;
    private GameId gameId;
    private PlayerId playerId;
    private float price;
    LocalDateTime createdAt;

    public Transaction(UUID transactionId, GameId gameId, PlayerId playerId, float price, LocalDateTime createdAt) {
        this.transactionId = transactionId;
        this.gameId = gameId;
        this.playerId = playerId;
        this.price = price;
        this.createdAt = createdAt;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public GameId getGameId() {
        return gameId;
    }

    public PlayerId getPlayerId() {
        return playerId;
    }

    public float getPrice() {
        return price;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
