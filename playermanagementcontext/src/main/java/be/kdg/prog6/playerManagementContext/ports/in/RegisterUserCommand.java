package be.kdg.prog6.playerManagementContext.ports.in;

import java.util.UUID;

public record RegisterUserCommand(UUID playerId, String name) {


}
