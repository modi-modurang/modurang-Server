package modi.modurang.domain.email.controller;

import lombok.RequiredArgsConstructor;
import modi.modurang.domain.email.dto.request.EmailRequest;
import modi.modurang.domain.email.service.EmailVerificationService;
import modi.modurang.global.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailVerificationService emailVerificationService;

    @PostMapping("/send")
    public ResponseEntity<String> sendVerificationCode(@RequestBody EmailRequest request) {
        try {
            emailVerificationService.sendVerificationCode(request.getEmail());
            return ResponseEntity.status(HttpStatus.OK).body("인증 코드 발송 성공");
        } catch (CustomException e) {
            return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body("인증 코드 발송 실패: " + e.getMessage());
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestBody EmailRequest request) {
        try {
            emailVerificationService.verifyCode(request.getEmail(), request.getCode());
            return ResponseEntity.status(HttpStatus.OK).body("이메일 인증 성공");
        } catch (CustomException e) {
            return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body("이메일 인증 실패: " + e.getMessage());
        }
    }
}
