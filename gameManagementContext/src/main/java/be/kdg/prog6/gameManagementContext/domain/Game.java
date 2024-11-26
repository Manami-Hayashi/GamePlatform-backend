package be.kdg.prog6.gameManagementContext.domain;

import java.math.BigDecimal;

public class Game {
    private final GameId gameId;
    private final String gameName;
    private final BigDecimal price;
    private final String description;

    public Game(GameId gameId, String gameName, BigDecimal price, String description) {
        this.gameId = gameId;
        this.gameName = gameName;
        this.price = price;
        this.description = description;
    }

    public GameId getGameId() {
        return gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }
}