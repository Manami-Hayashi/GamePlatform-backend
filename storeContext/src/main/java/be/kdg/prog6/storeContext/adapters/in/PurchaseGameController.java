package be.kdg.prog6.storeContext.adapters.in;

import be.kdg.prog6.storeContext.port.in.PurchaseGameCommand;
import be.kdg.prog6.storeContext.port.in.PurchaseGameUseCase;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/store")
public class PurchaseGameController {
    private final PurchaseGameUseCase purchaseGameUseCase;
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(PurchaseGameController.class);

    public PurchaseGameController(PurchaseGameUseCase purchaseGameUseCase) {
        this.purchaseGameUseCase = purchaseGameUseCase;
    }

    @PostMapping("/purchase")
    public ResponseEntity<Void> purchaseGame(@RequestBody PurchaseGameCommand command) {
        logger.info("Received purchase request for gameId: {} by playerId: {}", command.gameId(), command.customerId());
        try {
            purchaseGameUseCase.purchaseGame(command);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Purchase failed for gameId: {} by playerId: {}", command.gameId(), command.customerId(), e);
            return ResponseEntity.badRequest().build();
        }
    }
}