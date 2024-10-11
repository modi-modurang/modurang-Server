package modi.modurang.domain.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import modi.modurang.domain.auth.dto.request.LoginRequest;
import modi.modurang.domain.auth.dto.request.ReissueRequest;
import modi.modurang.domain.auth.dto.request.SignUpRequest;
import modi.modurang.domain.auth.service.AuthService;
import modi.modurang.global.common.BaseResponse;
import modi.modurang.global.security.jwt.dto.Jwt;
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
    public ResponseEntity<BaseResponse<Void>> signup(@Valid @RequestBody SignUpRequest request) {
        authService.signup(request);

        return BaseResponse.of(null, 201);
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<Jwt>> login(@RequestBody LoginRequest request) {
        return BaseResponse.of(authService.login(request));
    }

    @PostMapping("/reissue")
    public ResponseEntity<BaseResponse<Jwt>> reissue(@RequestBody ReissueRequest request) {
        return BaseResponse.of(authService.reissue(request));
    }
}
