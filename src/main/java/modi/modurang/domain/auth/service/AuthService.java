package modi.modurang.domain.auth.service;

import lombok.RequiredArgsConstructor;
import modi.modurang.domain.auth.dto.request.LoginRequest;
import modi.modurang.domain.auth.dto.request.ReissueRequest;
import modi.modurang.domain.auth.dto.request.SignUpRequest;
import modi.modurang.domain.auth.repository.RefreshTokenRepository;
import modi.modurang.domain.email.entity.Email;
import modi.modurang.domain.email.repository.EmailRepository;
import modi.modurang.domain.user.entity.User;
import modi.modurang.domain.user.repository.UserRepository;
import modi.modurang.global.exception.CustomException;
import modi.modurang.global.exception.ErrorCode;
import modi.modurang.global.security.jwt.dto.Jwt;
import modi.modurang.global.security.jwt.provider.JwtProvider;
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
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void signup(SignUpRequest request) {
        if (userRepository.existsByStudentNumber(request.getStudentNumber())) {
            throw new CustomException(ErrorCode.HAS_STUDENTNUMBER);
        } else if (userRepository.findByEmail(request.getEmail()).isPresent()) {
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
                .club(null)
                .build();

        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public Jwt login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.WRONG_PASSWORD);
        }

        return jwtProvider.generateToken(request.getEmail());
    }

    @Transactional(readOnly = true)
    public Jwt reissue(ReissueRequest request) {
        String email = jwtProvider.getSubject(request.getRefreshToken());

        String refreshToken = request.getRefreshToken().substring(8);

        if (userRepository.findByEmail(email).isEmpty()) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        if (!refreshTokenRepository.existsByEmail(email)) {
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        if (!refreshTokenRepository.findByEmail(email).equals(refreshToken)) {
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        return jwtProvider.generateToken(email);
    }
}
