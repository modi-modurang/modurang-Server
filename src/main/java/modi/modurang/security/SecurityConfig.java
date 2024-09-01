package modi.modurang.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // 이 클래스가 Spring의 설정 클래스임을 나타냅니다.
@EnableWebSecurity // Spring Security를 활성화하여 웹 애플리케이션의 보안을 설정합니다.
public class SecurityConfig {

    // PasswordEncoder 빈을 생성하여 비밀번호 암호화를 위한 BCryptPasswordEncoder를 반환합니다.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // BCrypt 알고리즘을 사용하여 비밀번호를 암호화합니다.
    }

    // SecurityFilterChain 빈을 생성하여 HTTP 요청에 대한 보안 필터 체인을 구성합니다.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests -> // HTTP 요청에 대한 인가 규칙을 설정합니다.
                        authorizeRequests
                                .requestMatchers("/auth/signup").permitAll() // "/auth/signup" 경로는 인증 없이 접근할 수 있도록 허용합니다.
                                .anyRequest().authenticated() // 그 외의 모든 요청은 인증을 요구합니다.
                )
                .csrf(AbstractHttpConfigurer::disable); // CSRF 보호 기능을 비활성화합니다. (REST API 등에서 주로 사용)

        return http.build(); // 구성된 SecurityFilterChain 객체를 반환합니다.
    }

}
