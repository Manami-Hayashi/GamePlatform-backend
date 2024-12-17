package be.kdg.prog6.lobbyManagementContext.ports.in;

import java.util.UUID;

public class MatchPlayersCommand {
    private final UUID playerId;
    private final UUID friendId;
    private final UUID gameId;

    // Constructor for random match (only playerId and gameId needed)
    public MatchPlayersCommand(UUID playerId, UUID gameId) {
        this.playerId = playerId;
        this.friendId = null;  // Not used for random matching
        this.gameId = gameId;
    }

    // Constructor for friend match (both playerId, friendId, and gameId needed)
    public MatchPlayersCommand(UUID playerId, UUID friendId, UUID gameId) {
        this.playerId = playerId;
        this.friendId = friendId;
        this.gameId = gameId;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public UUID getFriendId() {
        return friendId;
    }

    public UUID getGameId() {
        return gameId;
    }
}
