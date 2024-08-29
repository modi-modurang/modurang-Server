package modi.modurang.auth;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
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
    public void signup(@RequestBody SignupDto signupRequest) {
        userService.saveUser(signupRequest);
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
        }
    }

    @PostMapping("/emails/verification-requests")
    public ResponseEntity<Void> sendVerificationCode(@RequestParam("email") @Valid @Email String email) {
        emailVerificationService.sendVerificationCode(email);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/emails/verifications")
    public ResponseEntity<String> verifyEmail(@RequestParam("email") String email,
                                              @RequestParam("code") String code) {
        boolean verified = emailVerificationService.verifyCode(email, code);
        if (verified) {
            return ResponseEntity.ok("이메일 인증이 완료되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("인증 코드가 유효하지 않거나 만료되었습니다.");
        }
    }
}
