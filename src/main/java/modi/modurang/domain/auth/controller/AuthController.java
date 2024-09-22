package modi.modurang.domain.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import modi.modurang.domain.auth.dto.request.LoginRequest;
import modi.modurang.domain.auth.dto.request.ReissueRequest;
import modi.modurang.domain.auth.dto.request.SignUpRequest;
import modi.modurang.domain.auth.dto.response.LoginResponse;
import modi.modurang.domain.auth.dto.response.ReissueResponse;
import modi.modurang.domain.auth.dto.response.SignupResponse;
import modi.modurang.domain.auth.service.AuthService;
import modi.modurang.global.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@Valid @RequestBody SignUpRequest request) {
        try {
            authService.signup(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(new SignupResponse("회원가입 성공"));
        } catch (CustomException e) {
            return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(new SignupResponse("회원가입 실패: " + e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        try {
            LoginResponse loginResponse = authService.login(request);
            return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
        } catch (CustomException e) {
            return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(new LoginResponse(null, null, "로그인 실패: " + e.getMessage()));
        }
    }

    @PostMapping("/reissue")
    public ResponseEntity<ReissueResponse> refresh(@Valid @RequestBody ReissueRequest request) {
        try {
            String newAccessToken = authService.reissue(request.getRefreshToken());
            return ResponseEntity.status(HttpStatus.OK).body(new ReissueResponse(newAccessToken, "토큰 갱신 성공"));
        } catch (CustomException e) {
            return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(new ReissueResponse(null, "토큰 갱신 실패: " + e.getMessage()));
        }
    }
}
