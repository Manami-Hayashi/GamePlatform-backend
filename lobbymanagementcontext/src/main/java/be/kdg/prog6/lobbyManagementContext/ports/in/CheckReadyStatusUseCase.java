package be.kdg.prog6.lobbyManagementContext.ports.in;

import java.util.UUID;

@FunctionalInterface
public interface CheckReadyStatusUseCase {
    boolean checkReadyStatus(UUID playerId);
}