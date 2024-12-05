package be.kdg.prog6.lobbyManagementContext.ports.in;

import be.kdg.prog6.lobbyManagementContext.domain.PlayerId;

@FunctionalInterface
public interface ReadyUpPlayerUseCase {
    void readyUp(PlayerId playerId);
}