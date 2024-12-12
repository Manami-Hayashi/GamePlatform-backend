// storeContext/src/main/java/be/kdg/prog6/storeContext/adapters/out/StripePaymentAdapter.java
package be.kdg.prog6.storeContext.adapters.out;

import be.kdg.prog6.storeContext.port.out.StripePaymentPort;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.net.RequestOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
public class StripePaymentAdapter implements StripePaymentPort {
    private static final Logger logger = LoggerFactory.getLogger(StripePaymentAdapter.class);
    private static final String STRIPE_SECRET_KEY = "sk_test_51QRt4uE7E3AQDzNOGnRmk9mjcHCFhpS3evikckRW08kWj35a0wEWjNejZr818z0qWFyn9NbcXB5JX5qW8o5saGXR00ljyeJRmY";

    @Override
    public Charge createCharge(BigDecimal amount, String currency, String paymentToken, String description) throws StripeException {
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", amount.multiply(BigDecimal.valueOf(100)).intValue()); // amount in cents
        chargeParams.put("currency", currency);
        chargeParams.put("source", paymentToken);
        chargeParams.put("description", description);

        RequestOptions requestOptions = RequestOptions.builder().setApiKey(STRIPE_SECRET_KEY).build();
        return Charge.create(chargeParams, requestOptions);
    }
}