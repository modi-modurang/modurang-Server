package modi.modurang.security;

import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

// JWT 인증 토큰 클래스
@Getter
public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private final String token;  // JWT 토큰

    public JwtAuthenticationToken(UserDetails principal, String token, Collection<? extends GrantedAuthority> authorities) {
        super(principal, null, authorities);
        this.token = token;
    }
}
