package be.kdg.prog6.gameManagementContext.ports.in;

import java.util.UUID;

public record RegisterUserCommand(UUID playerId, String name) {
}
