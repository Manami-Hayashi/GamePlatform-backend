package be.kdg.prog6.common.events;

import java.util.UUID;

public class GamePurchasedEvent {
    private UUID playerId;
    private UUID gameId;
    private String gameName;

    public GamePurchasedEvent(UUID playerId, UUID gameId, String gameName) {
        this.playerId = playerId;
        this.gameId = gameId;
        this.gameName = gameName;
    }

    public GamePurchasedEvent() {
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public UUID getGameId() {
        return gameId;
    }

    public String getGameName() {
        return gameName;
    }
}