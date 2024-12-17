// storeContext/src/main/java/be/kdg/prog6/storeContext/core/PurchaseGameUseCaseImpl.java
package be.kdg.prog6.storeContext.core;

import be.kdg.prog6.storeContext.domain.CustomerId;
import be.kdg.prog6.storeContext.domain.GameId;
import be.kdg.prog6.storeContext.domain.Transaction;
import be.kdg.prog6.storeContext.port.in.PurchaseGameCommand;
import be.kdg.prog6.storeContext.port.in.PurchaseGameUseCase;
import be.kdg.prog6.storeContext.port.out.LoadStoreGamePort;
import be.kdg.prog6.storeContext.port.out.StripePaymentPort;
import be.kdg.prog6.storeContext.port.out.TransactionCreatedPort;
import be.kdg.prog6.storeContext.port.out.UpdatePurchasePort;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PurchaseGameUseCaseImpl implements PurchaseGameUseCase {
    private final LoadStoreGamePort loadStoreGamePort;
    private final TransactionCreatedPort transactionCreatedPort;
    private final UpdatePurchasePort updatePurchasePort;
    private final StripePaymentPort stripePaymentPort;
    private static final Logger logger = LoggerFactory.getLogger(PurchaseGameUseCaseImpl.class);

    public PurchaseGameUseCaseImpl(LoadStoreGamePort loadStoreGamePort, TransactionCreatedPort transactionCreatedPort, UpdatePurchasePort updatePurchasePort, StripePaymentPort stripePaymentPort) {
        this.loadStoreGamePort = loadStoreGamePort;
        this.transactionCreatedPort = transactionCreatedPort;
        this.updatePurchasePort = updatePurchasePort;
        this.stripePaymentPort = stripePaymentPort;
    }

    @Override
    public void purchaseGame(PurchaseGameCommand command) {
        logger.info("Processing purchase of game: {} for player: {}", command.gameId(), command.customerId());
        var storeGame = loadStoreGamePort.findById(new GameId(command.gameId()));

        if (storeGame == null) {
            logger.error("Game not found: {}", command.gameId());
            throw new IllegalArgumentException("Game not found");
        }

        try {
            Charge charge = stripePaymentPort.createCharge(storeGame.getPrice(), "eur", command.paymentToken(), "Purchase of game: " + storeGame.getGameName());

            if ("succeeded".equals(charge.getStatus())) {
                Transaction transaction = new Transaction(
                        UUID.randomUUID(),
                        new GameId(command.gameId()),
                        new CustomerId(command.customerId()),
                        storeGame.getPrice().floatValue(),
                        LocalDateTime.now()
                );
                transactionCreatedPort.createTransaction(transaction);
                logger.info("Game purchased successfully: {}", storeGame.getGameName());
                logger.info("Player with id {} purchased the game with id {} for euros {}", command.customerId(), command.gameId(), storeGame.getPrice());

                String gameName = storeGame.getGameName();
                updatePurchasePort.publish(command.customerId(), command.gameId(), gameName);
            } else {
                throw new RuntimeException("Payment failed");
            }
        } catch (StripeException e) {
            logger.error("Stripe error: {}", e.getMessage());
            throw new RuntimeException("Payment processing failed", e);
        }
    }
}