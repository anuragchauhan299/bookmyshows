package movie.service.bookmyshow.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "bookshow.payment")
@Getter
@Setter
@RequiredArgsConstructor
public class PaymentProperties {
    // Fallback accessor if Lombok processing is disabled for any reason
    private String gateway;
    private int timeoutSeconds;
    private int retryAttempts;
    private List<String> enabledGateways;

}
