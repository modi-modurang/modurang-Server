package modi.modurang.controller;

import lombok.RequiredArgsConstructor;
<<<<<<< HEAD
import modi.modurang.dto.LoginRequestDto;
import modi.modurang.dto.LoginResponseDto;
import modi.modurang.dto.SignupDto;
import modi.modurang.service.EmailVerificationService;
import modi.modurang.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final EmailVerificationService emailVerificationService;

    @PostMapping("/signup")
    public void signup(@RequestBody SignupDto signupDto) {
        userService.saveUser(signupDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequest) {
        try {
            LoginResponseDto response = userService.login(loginRequest.getStudentNumber(), loginRequest.getPassword());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new LoginResponseDto(null, null, "로그인 실패: " + e.getMessage()));
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refresh(@RequestBody String refreshToken) {
        try {
            String newAccessToken = userService.refreshAccessToken(refreshToken);
            return ResponseEntity.ok(new LoginResponseDto(newAccessToken, refreshToken, "액세스 토큰 갱신 성공"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new LoginResponseDto(null, null, "토큰 갱신 실패: " + e.getMessage()));
=======
import modi.modurang.dto.SignupRequestDto;
import modi.modurang.dto.SignupResponseDto;
import modi.modurang.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // 이 클래스가 REST API의 컨트롤러임을 나타냅니다.
@RequestMapping("/auth") // '/auth' 경로와 관련된 요청을 처리합니다.
@RequiredArgsConstructor // final 필드에 대한 생성자를 자동으로 생성합니다.
public class AuthController {

    private final UserService userService; // 회원 관련 비즈니스 로직을 처리하는 서비스

    @PostMapping("/signup") // HTTP POST 요청을 '/auth/signup' 경로로 매핑합니다.
    public ResponseEntity<SignupResponseDto> signup(@RequestBody SignupRequestDto signupRequest) {
        try {
            // 회원가입 요청을 처리하고, 성공 시 회원 정보를 저장합니다.
            userService.UserSave(signupRequest.getUsername(), signupRequest.getPassword(), signupRequest.getStudentNumber());
            return ResponseEntity.ok(new SignupResponseDto("회원가입 성공")); // 회원가입 성공 메시지를 반환합니다.
        } catch (Exception e) {
            // 예외 발생 시 실패 메시지를 반환합니다.
            return ResponseEntity.badRequest().body(new SignupResponseDto("회원가입 실패: " + e.getMessage()));
>>>>>>> feature/announcements
        }
    }
}
