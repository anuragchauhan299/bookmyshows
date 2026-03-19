package movie.service.bookmyshow.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "bookshow.security")
@Getter
@Setter
@RequiredArgsConstructor
public class SecurityProperties {
    private String jwtSecret;
    private long jwtExpirationMs;
}
