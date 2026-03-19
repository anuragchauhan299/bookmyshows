package movie.service.bookmyshow.paymentgateway;

import lombok.extern.slf4j.Slf4j;
import movie.service.bookmyshow.constants.AppConstants;

import java.util.UUID;

@Slf4j
public class StripePaymentGateway implements PaymentGateway {

    @Override
    public PaymentResult processPayment(PaymentRequest request) {
        log.info("Processing Stripe payment for: {}", request.getBookingReference());

        String paymentId = AppConstants.Payment.ID_PREFIX_STRIPE + UUID.randomUUID().toString().replace("-", "").substring(0, 16);

        return PaymentResult.builder()
                .success(true)
                .paymentId(paymentId)
                .status(AppConstants.Payment.STATUS_CAPTURED)
                .message(AppConstants.Payment.MESSAGE_PAYMENT_SUCCESS + "Stripe")
                .gatewayResponse("{\"id\":\"" + paymentId + "\",\"status\":\"succeeded\"}")
                .build();
    }


}
