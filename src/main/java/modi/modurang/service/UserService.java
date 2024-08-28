package modi.modurang.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import modi.modurang.domain.User;
import modi.modurang.dto.LoginResponseDto;
import modi.modurang.dto.SignupDto;
import modi.modurang.exception.CustomException;
import modi.modurang.exception.ErrorCode;
import modi.modurang.repository.UserRepository;
import modi.modurang.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public void saveUser(SignupDto signupDto) throws CustomException {
        if (userRepository.existsByStudentNumber(signupDto.getStudentNumber())) {
            throw new CustomException(ErrorCode.HAS_STUDENTNUMBER);
        }
        User user = new User();
        user.setUsername(signupDto.getUsername());
        user.setPassword(passwordEncoder.encode(signupDto.getPassword()));
        user.setStudentNumber(signupDto.getStudentNumber());
        userRepository.save(user);
    }

    public User authenticateUser(String studentNumber, String password) {
        User user = userRepository.findByStudentNumber(studentNumber)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        } else {
            return null;
        }
    }

    public LoginResponseDto login(String studentNumber, String password) {
        User user = authenticateUser(studentNumber, password);
        if (user != null) {
            String accessToken = jwtUtil.generateToken(user.getUsername());
            return new LoginResponseDto(accessToken, "로그인 성공");
        } else {
            return new LoginResponseDto(null, "로그인 실패: 잘못된 사용자 이름 또는 비밀번호");
        }
    }
}
