package modi.modurang.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter // 모든 필드에 대한 Getter 메서드를 자동으로 생성합니다.
@ConfigurationProperties(prefix = "jwt") // 'jwt' 접두어로 시작하는 프로퍼티를 클래스의 필드에 바인딩합니다.
@RequiredArgsConstructor // 모든 final 필드에 대한 생성자를 자동으로 생성합니다.
public class JwtProperties {

    private final String secretKey; // JWT 서명을 위한 비밀 키, 토큰의 무결성을 확인하는 데 사용됩니다.
    private final Long accessTokenExpiration; // 액세스 토큰의 만료 시간(밀리초 단위), 토큰의 유효 기간을 정의합니다.
}