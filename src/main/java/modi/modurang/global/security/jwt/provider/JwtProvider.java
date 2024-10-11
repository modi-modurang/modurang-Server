package modi.modurang.global.security.jwt.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import modi.modurang.global.exception.CustomException;
import modi.modurang.global.exception.ErrorCode;
import modi.modurang.global.security.jwt.config.JwtProperties;
import modi.modurang.global.security.jwt.enums.JwtType;
import org.springframework.stereotype.Component;
import modi.modurang.global.security.jwt.dto.Jwt;

import java.util.Date;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final JwtProperties jwtProperties;

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            String tokenWithoutPrefix;

            if (token.startsWith("Bearer ")) {
                tokenWithoutPrefix = token.substring(7);
            } else if (token.startsWith("Refresh ")) {
                tokenWithoutPrefix = token.substring(8);
            } else {
                throw new CustomException(ErrorCode.UNSUPPORTED_TOKEN_TYPE);
            }

            String secretKey = getSecretKeyForToken(token);

            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(tokenWithoutPrefix)
                    .getBody();
        } catch (SignatureException e) {
            throw new CustomException(ErrorCode.INVALID_SIGNATURE);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
    }

    private String getSecretKeyForToken(String token) {
        if (token.startsWith(jwtProperties.getAccessTokenPrefix())) {
            return jwtProperties.getAccessTokenSecretKey();
        } else if (token.startsWith(jwtProperties.getRefreshTokenPrefix())) {
            return jwtProperties.getRefreshTokenSecretKey();
        } else {
            throw new CustomException(ErrorCode.UNSUPPORTED_TOKEN_TYPE);
        }
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Jwt generateToken(String email) {
        Date now = new Date();

        String accessToken = Jwts.builder()
                .setHeaderParam("typ", JwtType.ACCESS.name())
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + jwtProperties.getAccessTokenExpiration()))
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getAccessTokenSecretKey())
                .compact();

        String refreshToken = Jwts.builder()
                .setHeaderParam("typ", JwtType.REFRESH.name())
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + jwtProperties.getRefreshTokenExpiration()))
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getRefreshTokenSecretKey())
                .compact();

        return new Jwt(accessToken, refreshToken);
    }

    public Boolean validateToken(String token, String email) {
        final String extractedUsername = extractEmail(token);
        return (extractedUsername.equals(email) && !isTokenExpired(token));
    }
}
