package modi.modurang.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import modi.modurang.dto.LoginDto;
import modi.modurang.dto.SignupDto;
import modi.modurang.service.EmailVerificationService;
import modi.modurang.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final EmailVerificationService emailVerificationService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupDto signupDto) {
        try {
            userService.saveUser(signupDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원가입 실패: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginDto> login(@RequestParam("studentNumber") String studentNumber,
                                          @RequestParam("password") String password) {
        try {
            LoginDto response = userService.login(studentNumber, password);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginDto(null, null, "로그인 실패: " + e.getMessage()));
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginDto> refresh(@RequestBody String refreshToken) {
        try {
            String newAccessToken = userService.refreshAccessToken(refreshToken);
            return ResponseEntity.ok(new LoginDto(newAccessToken, refreshToken, "액세스 토큰 갱신 성공"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginDto(null, null, "토큰 갱신 실패: " + e.getMessage()));
        }
    }

    @PostMapping("/email/send")
    public ResponseEntity<String> sendVerificationCode(@RequestParam("email") @Valid @Email String email) {
        try {
            emailVerificationService.sendVerificationCode(email);
            return ResponseEntity.ok("인증 코드 발송 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증 코드 발송 실패: " + e.getMessage());
        }
    }

    @PostMapping("/email/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam("email") @Valid @Email String email,
                                              @RequestParam("code") String code) {
        try {
            emailVerificationService.verifyCode(email, code);
            return ResponseEntity.ok("이메일 인증 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이메일 인증 실패: " + e.getMessage());
        }
    }
}
