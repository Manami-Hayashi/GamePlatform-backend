// common/src/main/java/be/kdg/prog6/common/events/GamePurchasedEvent.java
package be.kdg.prog6.common.events;

import be.kdg.prog6.common.domain.PlayerId;

import java.util.UUID;

public class GamePurchasedEvent {
    private PlayerId playerId;
    private UUID gameId;
    private String gameName;

    public GamePurchasedEvent(PlayerId playerId, UUID gameId, String gameName) {
        this.playerId = playerId;
        this.gameId = gameId;
        this.gameName = gameName;
    }

    public GamePurchasedEvent() {
    }

    public PlayerId getPlayerId() {
        return playerId;
    }

    public UUID getGameId() {
        return gameId;
    }

    public String getGameName() {
        return gameName;
    }
}