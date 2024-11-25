package be.kdg.prog6.PlayerManagementContext.port.in;

import java.util.UUID;

public record RegisterUserCommand(UUID playerId, String name) {


}
