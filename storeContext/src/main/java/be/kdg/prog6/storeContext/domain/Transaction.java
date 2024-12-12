package be.kdg.prog6.storeContext.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {
    private UUID transactionId;
    private GameId gameId;
    private CustomerId customerId;
    private float price;
    LocalDateTime createdAt;

    public Transaction(UUID transactionId, GameId gameId, CustomerId customerId, float price, LocalDateTime createdAt) {
        this.transactionId = transactionId;
        this.gameId = gameId;
        this.customerId = customerId;
        this.price = price;
        this.createdAt = createdAt;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public GameId getGameId() {
        return gameId;
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public float getPrice() {
        return price;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
