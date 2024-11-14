package modi.modurang.domain.user.service;

import lombok.RequiredArgsConstructor;
import modi.modurang.domain.user.dto.response.UserResponse;
import modi.modurang.domain.user.entity.User;
import modi.modurang.domain.user.error.UserError;
import modi.modurang.domain.user.repository.UserRepository;
import modi.modurang.global.error.CustomException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public UserResponse getMe() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(UserError.USER_NOT_FOUND));

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getStudentNumber(),
                user.getEmail(),
                user.getClub()
        );
    }
}
