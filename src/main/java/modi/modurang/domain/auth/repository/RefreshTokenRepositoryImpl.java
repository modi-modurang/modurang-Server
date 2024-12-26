package modi.modurang.domain.auth.repository;

import lombok.RequiredArgsConstructor;
import modi.modurang.global.security.jwt.config.JwtProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {
    private final RedisTemplate<String, String> redisTemplate;
    private final JwtProperties jwtProperties;

    @Override
    public void save(String email, String refreshToken) {
        redisTemplate.opsForValue().set(
                "refreshToken:" + email,
                refreshToken,
                jwtProperties.getRefreshTokenExpiration(),
                TimeUnit.MILLISECONDS
        );
    }

    @Override
    public String findByEmail(String email) {
        return redisTemplate.opsForValue().get("refreshToken:" + email);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return redisTemplate.hasKey("refreshToken:" + email);
    }
}