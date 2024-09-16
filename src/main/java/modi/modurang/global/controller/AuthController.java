package modi.modurang.global.controller;

import lombok.RequiredArgsConstructor;
import modi.modurang.domain.user.dto.LoginRequestDto;
import modi.modurang.domain.user.dto.LoginResponseDto;
import modi.modurang.domain.user.dto.SignUpRequestDto;
import modi.modurang.domain.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignUpRequestDto signUpRequestDto) {
        try {
            userService.saveUser(signUpRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원가입 실패: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        try {
            LoginResponseDto loginResponseDto = userService.login(loginRequestDto);
            return ResponseEntity.ok(loginResponseDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponseDto(null, null, "로그인 실패: " + e.getMessage()));
        }
    }
}
