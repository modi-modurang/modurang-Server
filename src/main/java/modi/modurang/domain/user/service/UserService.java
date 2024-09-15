package modi.modurang.domain.user.service;

import lombok.RequiredArgsConstructor;
import modi.modurang.domain.user.dto.LoginResponse;
import modi.modurang.domain.user.dto.SignUpRequest;
import modi.modurang.domain.user.entity.User;
import modi.modurang.global.exception.CustomException;
import modi.modurang.global.exception.ErrorCode;
import modi.modurang.domain.user.repository.UserRepository;
import modi.modurang.global.security.JwtProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    private static final String LOGIN_SUCCESS_MESSAGE = "로그인 성공";

    @Transactional
    public void saveUser(SignUpRequest signUpRequest) {
        if (userRepository.existsByStudentNumber(signUpRequest.getStudentNumber())) {
            throw new CustomException(ErrorCode.HAS_STUDENTNUMBER);
        }
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setStudentNumber(signUpRequest.getStudentNumber());
        user.setEmail(signUpRequest.getEmail());
        userRepository.save(user);
    }

    public LoginResponse login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (passwordEncoder.matches(password, user.getPassword())) {
            String accessToken = jwtProvider.generateAccessToken(user.getUsername());
            String refreshToken = jwtProvider.generateRefreshToken(user.getUsername());
            return new LoginResponse(accessToken, refreshToken, LOGIN_SUCCESS_MESSAGE);
        } else {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }
    }

    public String refreshAccessToken(String refreshToken) {
        String username = jwtProvider.extractUsername(refreshToken);
        if (jwtProvider.validateToken(refreshToken, username)) {
            return jwtProvider.generateAccessToken(username);
        } else {
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }
    }
}
