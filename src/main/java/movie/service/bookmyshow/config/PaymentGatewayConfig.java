package movie.service.bookmyshow.config;

import movie.service.bookmyshow.paymentgateway.PaymentGateway;
import movie.service.bookmyshow.paymentgateway.RazorpayPaymentGateway;
import movie.service.bookmyshow.paymentgateway.StripePaymentGateway;
import movie.service.bookmyshow.properties.PaymentProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentGatewayConfig {

    private final PaymentProperties paymentProperties;

    public PaymentGatewayConfig(PaymentProperties paymentProperties) {
        this.paymentProperties = paymentProperties;
    }


    @Bean
    public PaymentGateway paymentGateway() {
        String gateway = paymentProperties.getGateway();
        return switch (gateway.toLowerCase()) {
            case "razorpay" -> new RazorpayPaymentGateway();
            case "stripe" -> new StripePaymentGateway();
            default -> new StripePaymentGateway();
        };
    }
}
