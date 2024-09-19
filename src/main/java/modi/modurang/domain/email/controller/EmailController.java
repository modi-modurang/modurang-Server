package modi.modurang.domain.email.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import modi.modurang.domain.email.service.EmailVerificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailVerificationService emailVerificationService;

    @PostMapping("/send")
    public ResponseEntity<String> sendVerificationCode(@RequestParam("email") @Valid @Email String email) {
        try {
            emailVerificationService.sendVerificationCode(email);
            return ResponseEntity.ok("인증 코드 발송 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증 코드 발송 실패: " + e.getMessage());
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam("email") @Valid @Email String email, @RequestParam("code") String code) {
        try {
            emailVerificationService.verifyCode(email, code);
            return ResponseEntity.ok("이메일 인증 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이메일 인증 실패: " + e.getMessage());
        }
    }
}
