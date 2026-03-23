package movie.service.bookmyshow.paymentgateway;

public interface PaymentGateway {
    PaymentResult processPayment(PaymentRequest request);
}
