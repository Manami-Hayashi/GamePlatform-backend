package be.kdg.prog6.storeContext.core;

import be.kdg.prog6.common.events.GamePurchasedEvent;
import be.kdg.prog6.storeContext.adapters.out.GamePurchasedEventPublisher;
import be.kdg.prog6.storeContext.domain.GameId;
import be.kdg.prog6.storeContext.domain.PlayerId;
import be.kdg.prog6.storeContext.domain.Transaction;
import be.kdg.prog6.storeContext.port.in.PurchaseGameCommand;
import be.kdg.prog6.storeContext.port.in.PurchaseGameUseCase;
import be.kdg.prog6.storeContext.port.out.LoadStoreGamePort;
import be.kdg.prog6.storeContext.port.out.TransactionCreatedPort;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.net.RequestOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class PurchaseGameUseCaseImpl implements PurchaseGameUseCase {
    private final LoadStoreGamePort loadStoreGamePort;
    private final TransactionCreatedPort transactionCreatedPort;
    private final GamePurchasedEventPublisher eventPublisher;
    private static final Logger logger = LoggerFactory.getLogger(PurchaseGameUseCaseImpl.class);

    public PurchaseGameUseCaseImpl(LoadStoreGamePort loadStoreGamePort, TransactionCreatedPort transactionCreatedPort, GamePurchasedEventPublisher gamePurchasedEventPublisher) {
        this.loadStoreGamePort = loadStoreGamePort;
        this.transactionCreatedPort = transactionCreatedPort;
        this.eventPublisher = gamePurchasedEventPublisher;
    }

    @Override
    public void purchaseGame(PurchaseGameCommand command) {
        logger.info("Processing purchase of game: {} for player: {}", command.gameId(), command.playerId());
        var storeGame = loadStoreGamePort.findById(new GameId(command.gameId()));

        if (storeGame == null) {
            logger.error("Game not found: {}", command.gameId());
            throw new IllegalArgumentException("Game not found");
        }

        try {
            // Create a charge using Stripe
            Map<String, Object> chargeParams = new HashMap<>();
            chargeParams.put("amount", storeGame.getPrice().multiply(BigDecimal.valueOf(100)).intValue()); // amount in cents
            chargeParams.put("currency", "eur");
            chargeParams.put("source", command.paymentToken());
            chargeParams.put("description", "Purchase of game: " + storeGame.getGameName());

            String stripeSecretKey = "sk_test_51QRt4uE7E3AQDzNOGnRmk9mjcHCFhpS3evikckRW08kWj35a0wEWjNejZr818z0qWFyn9NbcXB5JX5qW8o5saGXR00ljyeJRmY";

            RequestOptions requestOptions = RequestOptions.builder().setApiKey(stripeSecretKey).build();
            Charge charge = Charge.create(chargeParams, requestOptions);

            if ("succeeded".equals(charge.getStatus())) {
                // Create and save the transaction
                Transaction transaction = new Transaction(
                        UUID.randomUUID(),
                        new GameId(command.gameId()),
                        new PlayerId(command.playerId()),
                        storeGame.getPrice().floatValue(),
                        LocalDateTime.now()
                );
                transactionCreatedPort.createTransaction(transaction);
                logger.info("Game purchased successfully: {}", storeGame.getGameName());
                logger.info("Player with id {} purchased the game with id {} for euros {}", command.playerId(), command.gameId(), storeGame.getPrice());

                String gameName = storeGame.getGameName();

                // Publish the event
                GamePurchasedEvent event = new GamePurchasedEvent(command.playerId(), command.gameId(), gameName);
                eventPublisher.publish(event);

            } else {
                throw new RuntimeException("Payment failed");
            }
        } catch (StripeException e) {
            logger.error("Stripe error: {}", e.getMessage());
            throw new RuntimeException("Payment processing failed", e);
        }
    }
}