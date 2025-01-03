package modi.modurang.domain.auth.service;

import lombok.RequiredArgsConstructor;
import modi.modurang.domain.auth.dto.request.LoginRequest;
import modi.modurang.domain.auth.dto.request.ReissueRequest;
import modi.modurang.domain.auth.dto.request.SignUpRequest;
import modi.modurang.domain.auth.dto.request.UpdatePasswordRequest;
import modi.modurang.domain.auth.error.AuthError;
import modi.modurang.domain.auth.repository.RefreshTokenRepository;
import modi.modurang.domain.email.entity.Email;
import modi.modurang.domain.email.repository.EmailRepository;
import modi.modurang.domain.user.entity.User;
import modi.modurang.domain.user.error.UserError;
import modi.modurang.domain.user.repository.UserRepository;
import modi.modurang.global.error.CustomException;
import modi.modurang.global.security.annotation.CurrentUser;
import modi.modurang.global.security.jwt.dto.Jwt;
import modi.modurang.global.security.jwt.enums.JwtType;
import modi.modurang.global.security.jwt.error.JwtError;
import modi.modurang.global.security.jwt.provider.JwtProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final EmailRepository emailRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    @Override
    public void signup(SignUpRequest request) {
        if (userRepository.existsByStudentNumber(request.getStudentNumber())) {
            throw new CustomException(UserError.HAS_STUDENTNUMBER);
        } else if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new CustomException(UserError.HAS_EMAIL);
        }

        Email verification = emailRepository.findByEmail(request.getEmail()).orElse(null);
        if (verification == null || !verification.isVerified()) {
            throw new CustomException(UserError.EMAIL_NOT_VERIFIED);
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
    @Override
    public Jwt login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException(UserError.USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(AuthError.WRONG_PASSWORD);
        }

        return jwtProvider.generateToken(request.getEmail());
    }

    @Transactional(readOnly = true)
    @Override
    public Jwt reissue(ReissueRequest request) {
        String email = jwtProvider.getSubject(request.getRefreshToken());

        if (userRepository.findByEmail(email).isEmpty()) {
            throw new CustomException(UserError.USER_NOT_FOUND);
        }

        if (!refreshTokenRepository.existsByEmail(email)) {
            throw new CustomException(JwtError.INVALID_TOKEN);
        }

        if (!refreshTokenRepository.findByEmail(email).equals(request.getRefreshToken())) {
            throw new CustomException(JwtError.INVALID_REFRESH_TOKEN);
        }

        if (jwtProvider.getType(request.getRefreshToken()) != JwtType.REFRESH)
            throw new CustomException(JwtError.INVALID_TOKEN_TYPE);

        return jwtProvider.generateToken(email);
    }

    @Transactional
    @Override
    public void deleteAccount(@CurrentUser User user) {

        userRepository.delete(user);
    }

    @Transactional
    @Override
    public void updatePassword(@CurrentUser User user, UpdatePasswordRequest request) {

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new CustomException(AuthError.WRONG_PASSWORD);
        }

        user.toBuilder()
                .password(passwordEncoder.encode(request.getNewPassword()))
                .build();

        userRepository.save(user);
    }
}