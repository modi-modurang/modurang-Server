package modi.modurang.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "jwt")
@RequiredArgsConstructor
public class JwtProperties {
    private final String accessTokenSecretKey;
    private final String refreshTokenSecretKey;
    private final Long accessTokenExpiration;
    private final Long refreshTokenExpiration;
}
