package modi.modurang.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private final String accessTokenSecretKey;
    private final String refreshTokenSecretKey;
    private final Long accessTokenExpiration;
    private final Long refreshTokenExpiration;
    private final String accessTokenPrefix;
    private final String refreshTokenPrefix;
}
