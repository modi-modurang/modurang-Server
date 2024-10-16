package modi.modurang.global.security.jwt.provider;

import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import modi.modurang.domain.auth.repository.RefreshTokenRepository;
import modi.modurang.domain.user.entity.User;
import modi.modurang.domain.user.repository.UserRepository;
import modi.modurang.global.exception.CustomException;
import modi.modurang.global.exception.ErrorCode;
import modi.modurang.global.security.details.CustomUserDetails;
import modi.modurang.global.security.jwt.config.JwtProperties;
import modi.modurang.global.security.jwt.enums.JwtType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import modi.modurang.global.security.jwt.dto.Jwt;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final JwtProperties jwtProperties;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private SecretKey key;

    @PostConstruct
    protected void init() {
        key = new SecretKeySpec(
                jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS512.key().build().getAlgorithm()
        );
    }

    public Jwt generateToken(String email) {
        Date now = new Date();

        String accessToken = Jwts.builder()
                .setHeaderParam("typ", JwtType.ACCESS.name())
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + jwtProperties.getAccessTokenExpiration()))
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();

        String refreshToken = Jwts.builder()
                .setHeaderParam("typ", JwtType.REFRESH.name())
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + jwtProperties.getRefreshTokenExpiration()))
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();

        refreshTokenRepository.save(email, refreshToken);

        return new Jwt(accessToken, refreshToken);
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);

        if (getType(token) != JwtType.ACCESS) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        User user = userRepository.findByEmail(claims.getSubject()).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        UserDetails details = new CustomUserDetails(user);

        return new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities());
    }

    public String extractToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return token.substring(7);
        }

        return null;
    }

    public String getSubject(String token) {
        return getClaims(token).getSubject();
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            throw new CustomException(ErrorCode.EXPIRED_TOKEN);
        } catch (UnsupportedJwtException e) {
            throw new CustomException(ErrorCode.UNSUPPORTED_TOKEN);
        } catch (MalformedJwtException e) {
            throw new CustomException(ErrorCode.MALFORMED_TOKEN);
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
    }

    public JwtType getType(String token) {
        return JwtType.valueOf(Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getHeader().getType()
        );
    }
}
