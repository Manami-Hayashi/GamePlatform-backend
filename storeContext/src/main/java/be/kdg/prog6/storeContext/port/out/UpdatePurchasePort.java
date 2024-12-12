// storeContext/src/main/java/be/kdg/prog6/storeContext/port/out/UpdatePurchasePort.java
package be.kdg.prog6.storeContext.port.out;

import java.util.UUID;

public interface UpdatePurchasePort {
    void publish(UUID customerId, UUID gameId, String gameName);
}