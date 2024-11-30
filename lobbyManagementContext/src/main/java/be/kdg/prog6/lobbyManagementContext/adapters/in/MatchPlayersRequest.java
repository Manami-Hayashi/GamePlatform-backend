package be.kdg.prog6.lobbyManagementContext.adapters.in;

import java.util.UUID;

public class MatchPlayersRequest {
    private UUID playerId;
    private UUID friendId;
    private UUID gameId;

    // Getters and setters
    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }

    public UUID getFriendId() {
        return friendId;
    }

    public void setFriendId(UUID friendId) {
        this.friendId = friendId;
    }

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }
}