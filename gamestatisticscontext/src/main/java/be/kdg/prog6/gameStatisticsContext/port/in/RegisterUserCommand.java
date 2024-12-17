package be.kdg.prog6.gameStatisticsContext.port.in;

import java.util.UUID;

public record RegisterUserCommand(UUID playerId, String name) {
    public RegisterUserCommand {
        if (playerId == null) {
            throw new IllegalArgumentException("PlayerId must not be null");
        }
        if (name == null) {
            throw new IllegalArgumentException("Name must not be null");
        }
    }
}
