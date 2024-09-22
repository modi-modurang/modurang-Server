package modi.modurang.domain.auth.service;

import lombok.RequiredArgsConstructor;
import modi.modurang.domain.auth.dto.request.LoginRequest;
import modi.modurang.domain.auth.dto.request.SignUpRequest;
import modi.modurang.domain.auth.dto.response.LoginResponse;
import modi.modurang.domain.email.entity.Email;
import modi.modurang.domain.email.repository.EmailRepository;
import modi.modurang.domain.user.entity.User;
import modi.modurang.domain.user.repository.UserRepository;
import modi.modurang.global.exception.CustomException;
import modi.modurang.global.exception.ErrorCode;
import modi.modurang.global.security.provider.JwtProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final EmailRepository emailRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional
    public void signup(SignUpRequest request) {
        if (userRepository.existsByStudentNumber(request.getStudentNumber())) {
            throw new CustomException(ErrorCode.HAS_STUDENTNUMBER);
        } else if (request.getStudentNumber().length() != 4 || !request.getStudentNumber().matches("\\d{4}")) {
            throw new CustomException(ErrorCode.INVALID_STUDENTNUMBER);
        } else if (request.getUsername().contains(" ")) {
            throw new CustomException(ErrorCode.INVALID_USERNAME);
        } else if (request.getPassword().contains(" ")) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        } else if (emailRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new CustomException(ErrorCode.HAS_EMAIL);
        }

        Email verification = emailRepository.findByEmail(request.getEmail()).orElse(null);
        if (verification == null || !verification.isVerified()) {
            throw new CustomException(ErrorCode.EMAIL_NOT_VERIFIED);
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .studentNumber(request.getStudentNumber())
                .email(request.getEmail())
                .club(request.getClub())
                .build();

        userRepository.save(user);
    }

    @Transactional
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            String accessToken = jwtProvider.generateAccessToken(user.getUsername());
            String refreshToken = jwtProvider.generateRefreshToken(user.getUsername());
            return new LoginResponse(accessToken, refreshToken, "로그인 성공");
        } else {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }
    }

    @Transactional
    public String reissue(String refreshToken) {
        String username = jwtProvider.extractUsername(refreshToken);
        if (jwtProvider.validateToken(refreshToken, username)) {
            return jwtProvider.generateAccessToken(username);
        } else {
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }
    }
}
