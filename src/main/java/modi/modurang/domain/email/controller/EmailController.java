package modi.modurang.domain.email.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import modi.modurang.domain.email.dto.request.EmailSendRequest;
import modi.modurang.domain.email.dto.request.EmailVerifyRequest;
import modi.modurang.domain.email.service.EmailVerificationService;
import modi.modurang.global.common.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "email-verification", description = "이메일 인증 API")
@RestController
@RequestMapping("/email-verification")
@RequiredArgsConstructor
public class EmailController {

    private final EmailVerificationService emailVerificationService;

    @Operation(summary = "인증 코드 발송")
    @PostMapping("/send")
    public ResponseEntity<BaseResponse<Void>> sendVerificationCode(@Valid @RequestBody EmailSendRequest request) {
        emailVerificationService.sendVerificationCode(request.getEmail());
        return BaseResponse.of(null);
    }

    @Operation(summary = "인증 코드 확인")
    @PostMapping("/verify")
    public ResponseEntity<BaseResponse<Void>> verifyEmail(@Valid @RequestBody EmailVerifyRequest request) {
        emailVerificationService.verifyCode(request.getEmail(), request.getCode());
        return BaseResponse.of(null);
    }
}
