package movie.service.bookmyshow.paymentgateway;

import movie.service.bookmyshow.constants.AppConstants;
import org.springframework.stereotype.Service;

import java.util.UUID;

//@Slf4j
@Service
public class RazorpayPaymentGateway implements PaymentGateway {

    @Override
    public PaymentResult processPayment(PaymentRequest request) {
        // log.info("Processing Razorpay payment for: {}", request.getBookingReference());

        String paymentId = AppConstants.Payment.ID_PREFIX_RAZORPAY + UUID.randomUUID().toString().substring(0, 16);

        return PaymentResult.builder()
                .success(true)
                .paymentId(paymentId)
                .status(AppConstants.Payment.STATUS_CAPTURED)
                .message(AppConstants.Payment.MESSAGE_PAYMENT_SUCCESS + "Razorpay")
                .gatewayResponse("{\"id\":\"" + paymentId + "\",\"status\":\"captured\"}")
                .build();
    }


}
