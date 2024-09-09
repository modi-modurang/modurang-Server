package modi.modurang.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import modi.modurang.dto.LoginResponse;
import modi.modurang.dto.SignUpRequest;
import modi.modurang.entity.User;
import modi.modurang.exception.CustomException;
import modi.modurang.exception.ErrorCode;
import modi.modurang.repository.UserRepository;
import modi.modurang.security.JwtProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public void saveUser(SignUpRequest signUpRequest) throws CustomException {
        if (userRepository.existsByStudentNumber(signUpRequest.getStudentNumber())) {
            throw new CustomException(ErrorCode.HAS_STUDENTNUMBER);
        }
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setStudentNumber(signUpRequest.getStudentNumber());
        userRepository.save(user);
    }

    public LoginResponse login(String studentNumber, String password) {
        User user = userRepository.findByStudentNumber(studentNumber)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (passwordEncoder.matches(password, user.getPassword())) {
            String accessToken = jwtProvider.generateAccessToken(user.getUsername());
            String refreshToken = jwtProvider.generateRefreshToken(user.getUsername());
            return new LoginResponse(accessToken, refreshToken, "로그인 성공");
        } else {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }
    }

    public String refreshAccessToken(String refreshToken) throws CustomException {
        String username = jwtProvider.extractUsername(refreshToken);
        if (jwtProvider.validateToken(refreshToken, username)) {
            return jwtProvider.generateAccessToken(username);
        } else {
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }
    }
}
