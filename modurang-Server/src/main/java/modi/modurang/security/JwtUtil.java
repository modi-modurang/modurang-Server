package modi.modurang.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Slf4j // 로그 출력을 위한 Lombok 어노테이션
@Component // Spring의 컴포넌트로 등록하여 의존성 주입을 가능하게 함
public class JwtUtil {

    @Value("${jwt.secret-key}") // application.yml에서 jwt.secret-key 값을 주입받음
    private String secretKey;

    @Value("${jwt.access-token-expiration}") // application.yml에서 jwt.access-token-expiration 값을 주입받음
    private long expirationTime;

    // JWT 토큰에서 사용자 이름을 추출
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // JWT 토큰에서 만료 날짜를 추출
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // JWT 토큰에서 클레임을 추출하는 공통 메서드
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // JWT 토큰에서 모든 클레임을 추출
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey) // 서명 키 설정
                .parseClaimsJws(token) // 토큰 파싱 및 클레임 추출
                .getBody();
    }

    // JWT 토큰이 만료되었는지 확인
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // 사용자 이름을 기반으로 JWT 토큰을 생성
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username) // 토큰의 주제(사용자 이름) 설정
                .setIssuedAt(new Date(System.currentTimeMillis())) // 토큰 발급 시간 설정
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime)) // 토큰 만료 시간 설정
                .signWith(SignatureAlgorithm.HS256, secretKey) // 서명 알고리즘 및 비밀 키 설정
                .compact(); // 토큰 생성
    }

    // JWT 토큰의 유효성을 검사
    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token); // 토큰에서 추출한 사용자 이름
        return (extractedUsername.equals(username) && !isTokenExpired(token)); // 사용자 이름이 일치하고 토큰이 만료되지 않았는지 확인
    }
}
