package modi.modurang.domain.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import modi.modurang.domain.auth.dto.request.LoginRequest;
import modi.modurang.domain.auth.dto.request.ReissueRequest;
import modi.modurang.domain.auth.dto.request.SignUpRequest;
import modi.modurang.domain.auth.dto.request.UpdatePasswordRequest;
import modi.modurang.domain.auth.service.AuthService;
import modi.modurang.domain.user.entity.User;
import modi.modurang.global.common.BaseResponse;
import modi.modurang.global.security.annotation.CurrentUser;
import modi.modurang.global.security.jwt.dto.Jwt;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "auth", description = "인증 관련 API")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    public ResponseEntity<BaseResponse<Void>> signup(@Valid @RequestBody SignUpRequest request) {
        authService.signup(request);
        return BaseResponse.of(null, 201);
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ResponseEntity<BaseResponse<Jwt>> login(@Valid @RequestBody LoginRequest request) {
        return BaseResponse.of(authService.login(request));
    }

    @Operation(summary = "엑세스 토큰 재발급")
    @PostMapping("/reissue")
    public ResponseEntity<BaseResponse<Jwt>> reissue(@Valid @RequestBody ReissueRequest request) {
        return BaseResponse.of(authService.reissue(request));
    }

    @Operation(summary = "회원 탈퇴")
    @DeleteMapping
    public ResponseEntity<BaseResponse<Void>> deleteAccount(@CurrentUser User user) {
        authService.deleteAccount(user);
        return BaseResponse.of(null, 204);
    }

    @Operation(summary = "비밀번호 변경")
    @PatchMapping
    public ResponseEntity<BaseResponse<Void>> updatePassword(@CurrentUser User user, @Valid @RequestBody UpdatePasswordRequest request) {
        authService.updatePassword(user, request);
        return BaseResponse.of(null, 204);
    }
}