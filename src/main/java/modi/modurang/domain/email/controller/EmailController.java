package modi.modurang.domain.email.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import modi.modurang.domain.email.dto.request.EmailSendRequest;
import modi.modurang.domain.email.dto.request.EmailVerifyRequest;
import modi.modurang.domain.email.service.EmailVerificationService;
import modi.modurang.global.common.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailVerificationService emailVerificationService;

    @PostMapping("/send")
    public ResponseEntity<BaseResponse> sendVerificationCode(@Valid @RequestBody EmailSendRequest request) {
        emailVerificationService.sendVerificationCode(request.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse("인증 코드 발송 성공"));
    }

    @PostMapping("/verify")
    public ResponseEntity<BaseResponse> verifyEmail(@Valid @RequestBody EmailVerifyRequest request) {
        emailVerificationService.verifyCode(request.getEmail(), request.getCode());
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse("이메일 인증 성공"));
    }
}
