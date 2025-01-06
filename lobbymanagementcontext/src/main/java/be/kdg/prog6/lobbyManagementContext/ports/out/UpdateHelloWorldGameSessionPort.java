package be.kdg.prog6.lobbyManagementContext.ports.out;

import be.kdg.prog6.lobbyManagementContext.domain.GameSession;

@FunctionalInterface
public interface UpdateHelloWorldGameSessionPort {
    void updateHWGameSession(GameSession gameSession);
}
