package be.kdg.prog6.storeContext.adapters.out.db;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(catalog = "store", name = "transaction")
public class TransactionJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "transaction_id")
    private UUID transactionId;

    @Column(name = "game_id")
    private UUID gameId;

    @Column(name = "customer_id")
    private UUID customerId;

    @Column(name = "price")
    private float price;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public TransactionJpaEntity() {}

    public TransactionJpaEntity(UUID transactionId, UUID gameId, UUID playerId, float price, LocalDateTime createdAt) {
        this.transactionId = transactionId;
        this.gameId = gameId;
        this.customerId = customerId;
        this.price = price;
        this.createdAt = createdAt;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}