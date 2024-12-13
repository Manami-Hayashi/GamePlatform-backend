package be.kdg.prog6.lobbyManagementContext.ports.in;

import be.kdg.prog6.lobbyManagementContext.domain.GameSession;

public class ReadyUpResponse {
    private final boolean gameSessionStarted;
    private final GameSession gameSession;

    public ReadyUpResponse(boolean gameSessionStarted, GameSession gameSession) {
        this.gameSessionStarted = gameSessionStarted;
        this.gameSession = gameSession;
    }

    public boolean isGameSessionStarted() {
        return gameSessionStarted;
    }

    public GameSession getGameSession() {
        return gameSession;
    }
}