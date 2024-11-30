package be.kdg.prog6.lobbyManagementContext.ports.in;

import java.util.UUID;

public record RegisterUserCommand(UUID playerId, String name) {
}
