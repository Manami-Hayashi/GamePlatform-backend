// CheckAllPlayersReadyPort.java
package be.kdg.prog6.lobbyManagementContext.ports.out;

import java.util.UUID;

@FunctionalInterface
public interface CheckAllPlayersReadyPort {
    boolean areAllPlayersReady(UUID lobbyId);
}