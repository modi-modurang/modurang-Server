package modi.modurang.service;

import lombok.RequiredArgsConstructor;
<<<<<<< HEAD
import lombok.extern.slf4j.Slf4j;
import modi.modurang.entity.User;
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
            String accessToken = jwtUtil.generateAccessToken(user.getUsername());
            String refreshToken = jwtUtil.generateRefreshToken(user.getUsername());
            return new LoginResponseDto(accessToken, refreshToken, "로그인 성공");
        } else {
            return new LoginResponseDto(null, null, "로그인 실패: 잘못된 사용자 이름 또는 비밀번호");
        }
    }

    public String refreshAccessToken(String refreshToken) throws CustomException {
        String username = jwtUtil.extractUsername(refreshToken);
        if (jwtUtil.validateRefreshToken(refreshToken, username)) {
            return jwtUtil.generateAccessToken(username);
        } else {
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }
=======
import modi.modurang.entity.User;
import modi.modurang.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// 서비스 클래스, 비즈니스 로직을 담당합니다.
@Service
@RequiredArgsConstructor // 모든 final 필드에 대한 생성자를 자동으로 생성합니다.
public class UserService {

    private final UserRepository userRepository; // 데이터베이스 접근을 위한 레포지토리
    private final PasswordEncoder passwordEncoder; // 비밀번호 암호화를 위한 인코더

    // 회원 정보를 저장하는 메서드
    public void UserSave(String username, String password, int studentNumber) {
        User user = new User(); // 새로운 User 객체 생성
        user.setUsername(username); // 사용자 이름 설정
        user.setPassword(passwordEncoder.encode(password)); // 비밀번호를 암호화한 후 설정
        user.setStudentNumber(studentNumber); // 학번 설정
        userRepository.save(user); // 데이터베이스에 User 객체 저장
>>>>>>> feature/announcements
    }
}
