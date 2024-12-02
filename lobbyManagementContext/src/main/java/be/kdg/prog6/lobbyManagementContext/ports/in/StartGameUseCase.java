package be.kdg.prog6.lobbyManagementContext.ports.in;

import be.kdg.prog6.lobbyManagementContext.domain.GameSession;
import be.kdg.prog6.lobbyManagementContext.domain.PlayerId;

@FunctionalInterface
public interface StartGameUseCase {
    GameSession readyUp(PlayerId playerId);
}