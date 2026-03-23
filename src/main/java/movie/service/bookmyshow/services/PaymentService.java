package movie.service.bookmyshow.services;

import movie.service.bookmyshow.paymentgateway.PaymentGateway;
import movie.service.bookmyshow.paymentgateway.PaymentRequest;
import movie.service.bookmyshow.paymentgateway.PaymentResult;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentGateway paymentGateway;

    public PaymentService(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    public PaymentResult processPayment(PaymentRequest request) {
        return paymentGateway.processPayment(request);
    }
}
