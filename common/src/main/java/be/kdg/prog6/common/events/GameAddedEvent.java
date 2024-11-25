package be.kdg.prog6.common.events;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

public class GameAddedEvent implements Serializable {
    private UUID gameId;
    private String gameName;
    private BigDecimal price;
    private String description;

    public GameAddedEvent(UUID gameId, String gameName, BigDecimal price, String description) {
        this.gameId = gameId;
        this.gameName = gameName;
        this.price = price;
        this.description = description;
    }

    public UUID getGameId() {
        return gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "GameAddedEvent{" +
                "gameId=" + gameId +
                ", gameName='" + gameName + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }
}