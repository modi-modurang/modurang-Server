package modi.modurang.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import modi.modurang.security.JwtProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Slf4j // 로그 출력을 위한 Lombok 어노테이션
@Component // Spring의 컴포넌트로 등록하여 의존성 주입을 가능하게 함
@RequiredArgsConstructor
public class JwtUtil {

    private final JwtProperties jwtProperties;

    // JWT 토큰에서 사용자 이름을 추출
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // JWT 토큰에서 만료 날짜를 추출
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // JWT 토큰에서 클레임을 추출하는 공통 메서드
    // 이 메서드는 주어진 토큰에서 클레임을 추출하여 전달된 함수로 처리합니다.
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // JWT 토큰에서 모든 클레임을 추출
    // 비밀 키를 사용하여 토큰을 파싱하고, 클레임의 본문을 추출합니다.
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey()) // 서명 키 설정
                .parseClaimsJws(token) // 토큰 파싱 및 클레임 추출
                .getBody();
    }

    // JWT 토큰이 만료되었는지 확인
    // 토큰의 만료 시간을 현재 시간과 비교하여 만료 여부를 반환합니다.
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // 사용자 이름을 기반으로 JWT 토큰을 생성
    // 토큰에는 사용자 이름, 발급 시간, 만료 시간, 서명 알고리즘 등이 포함됩니다.
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username) // 토큰의 주제(사용자 이름) 설정
                .setIssuedAt(new Date(System.currentTimeMillis())) // 토큰 발급 시간 설정
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getAccessTokenExpiration())) // 토큰 만료 시간 설정
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey()) // 서명 알고리즘 및 비밀 키 설정
                .compact(); // 토큰 생성
    }

    // JWT 토큰의 유효성을 검사
    // 토큰에서 사용자 이름을 추출하여 전달된 사용자 이름과 비교하고, 토큰이 만료되지 않았는지 확인합니다.
    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token); // 토큰에서 추출한 사용자 이름
        return (extractedUsername.equals(username) && !isTokenExpired(token)); // 사용자 이름이 일치하고 토큰이 만료되지 않았는지 확인
    }
}
