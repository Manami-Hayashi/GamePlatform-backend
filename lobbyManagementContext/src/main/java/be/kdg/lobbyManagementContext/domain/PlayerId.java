package be.kdg.lobbyManagementContext.domain;

import java.util.UUID;

public record PlayerId(UUID id) {
    public PlayerId {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
    }

    public PlayerId(String id) {
        this(UUID.fromString(id));
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
