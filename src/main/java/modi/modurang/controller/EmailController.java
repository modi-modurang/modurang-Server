package modi.modurang.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import modi.modurang.service.EmailVerificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {
    private final EmailVerificationService emailVerificationService;

    @PostMapping("/verification-request")
    public ResponseEntity<Void> sendVerificationCode(@RequestParam("email") @Valid @Email String email) {
        emailVerificationService.sendVerificationCode(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verification")
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
