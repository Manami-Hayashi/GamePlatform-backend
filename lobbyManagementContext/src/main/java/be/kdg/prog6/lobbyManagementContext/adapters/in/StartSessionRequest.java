package be.kdg.prog6.lobbyManagementContext.adapters.in;

import java.util.UUID;

public class StartSessionRequest {
    private UUID lobbyId;

    // Getters and setters
    public UUID getLobbyId() {
        return lobbyId;
    }

    public void setLobbyId(UUID lobbyId) {
        this.lobbyId = lobbyId;
    }
}