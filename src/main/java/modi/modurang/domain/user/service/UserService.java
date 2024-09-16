package modi.modurang.domain.user.service;

import lombok.RequiredArgsConstructor;
import modi.modurang.domain.user.dto.LoginRequestDto;
import modi.modurang.domain.user.dto.LoginResponseDto;
import modi.modurang.domain.user.dto.SignUpRequestDto;
import modi.modurang.domain.user.entity.User;
import modi.modurang.global.exception.CustomException;
import modi.modurang.global.exception.ErrorCode;
import modi.modurang.domain.user.repository.UserRepository;
import modi.modurang.global.security.jwt.JwtProvider;
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
    public void saveUser(SignUpRequestDto signUpRequestDto) {
        if (userRepository.existsByStudentNumber(signUpRequestDto.getStudentNumber())) {
            throw new CustomException(ErrorCode.HAS_STUDENTNUMBER);
        }
        User user = new User();
        user.setUsername(signUpRequestDto.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));
        user.setStudentNumber(signUpRequestDto.getStudentNumber());
        user.setEmail(signUpRequestDto.getEmail());
        userRepository.save(user);
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            String accessToken = jwtProvider.generateAccessToken(user.getUsername());
            String refreshToken = jwtProvider.generateRefreshToken(user.getUsername());
            return new LoginResponseDto(accessToken, refreshToken, LOGIN_SUCCESS_MESSAGE);
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
