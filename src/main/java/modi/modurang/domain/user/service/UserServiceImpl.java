package modi.modurang.domain.user.service;

import lombok.RequiredArgsConstructor;
import modi.modurang.domain.user.dto.response.UserResponse;
import modi.modurang.domain.user.entity.User;
import modi.modurang.global.security.util.SecurityUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final SecurityUtil securityUtil;

    @Transactional(readOnly = true)
    @Override
    public UserResponse getMe() {
        User user = securityUtil.currentUser();

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getStudentNumber(),
                user.getEmail(),
                user.getClub()
        );
    }
}
