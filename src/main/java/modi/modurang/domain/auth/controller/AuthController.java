package modi.modurang.domain.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import modi.modurang.domain.auth.dto.request.LoginRequest;
import modi.modurang.domain.auth.dto.request.ReissueRequest;
import modi.modurang.domain.auth.dto.request.SignUpRequest;
import modi.modurang.domain.auth.dto.response.LoginResponse;
import modi.modurang.domain.auth.dto.response.ReissueResponse;
import modi.modurang.domain.auth.service.AuthService;
import modi.modurang.global.common.BaseResponse;
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
    public ResponseEntity<BaseResponse> signup(@Valid @RequestBody SignUpRequest request) {
        authService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse("회원가입 성공"));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse loginResponse = authService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
    }

    @PostMapping("/reissue")
    public ResponseEntity<ReissueResponse> reissue(@Valid @RequestBody ReissueRequest request) {
        String newAccessToken = authService.reissue(request.getRefreshToken());
        return ResponseEntity.status(HttpStatus.OK).body(new ReissueResponse(newAccessToken, "토큰 갱신 성공"));
    }
}
