package be.kdg.prog6.storeContext.port.in;

import java.util.UUID;

public record RegisterUserCommand(UUID customerId, String gameName) {
}
