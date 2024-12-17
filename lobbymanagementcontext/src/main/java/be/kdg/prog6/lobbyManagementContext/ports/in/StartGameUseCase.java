package be.kdg.prog6.lobbyManagementContext.ports.in;

import java.util.UUID;

public interface StartGameUseCase {
    ReadyUpResponse readyUp(UUID lobbyId);
}