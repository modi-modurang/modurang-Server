package modi.modurang.global.security.util;

import lombok.RequiredArgsConstructor;
import modi.modurang.domain.user.entity.User;
import modi.modurang.domain.user.error.UserError;
import modi.modurang.domain.user.repository.UserRepository;
import modi.modurang.global.error.CustomException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityUtil {
    private final UserRepository userRepository;

    /**
     * 현재 인증된 사용자의 정보를 조회합니다.
     *
     * @return 현재 인증된 사용자의 엔티티
     * @throws CustomException 인증된 사용자를 찾을 수 없는 경우 (USER_NOT_FOUND)
     */
    public User currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication.getPrincipal() instanceof UserDetails userDetails)) {
            throw new CustomException(UserError.USER_NOT_FOUND);
        }

        String email = userDetails.getUsername();
        return userRepository.findByEmail(email).orElseThrow(() -> new CustomException(UserError.USER_NOT_FOUND));
    }
}