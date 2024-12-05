package be.kdg.prog6.storeContext.port.in;

import java.util.UUID;

public record PurchaseGameCommand(UUID playerId, UUID gameId, String paymentToken) {
}
