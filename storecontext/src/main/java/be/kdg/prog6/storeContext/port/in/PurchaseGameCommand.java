// storeContext/src/main/java/be/kdg/prog6/storeContext/port/in/PurchaseGameCommand.java
package be.kdg.prog6.storeContext.port.in;

import java.util.UUID;

public record PurchaseGameCommand(UUID customerId, UUID gameId, String paymentToken) {
}