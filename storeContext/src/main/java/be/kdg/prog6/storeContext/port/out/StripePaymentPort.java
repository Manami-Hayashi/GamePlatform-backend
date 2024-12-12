// storeContext/src/main/java/be/kdg/prog6/storeContext/port/out/StripePaymentPort.java
package be.kdg.prog6.storeContext.port.out;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

import java.math.BigDecimal;

@FunctionalInterface
public interface StripePaymentPort {
    Charge createCharge(BigDecimal amount, String currency, String paymentToken, String description) throws StripeException;
}