package modi.modurang.auth;

import lombok.RequiredArgsConstructor;
import modi.modurang.domain.User;
import modi.modurang.dto.LoginRequestDto;
import modi.modurang.dto.LoginResponseDto;
import modi.modurang.dto.SignupRequestDto;
import modi.modurang.dto.SignupResponseDto;
import modi.modurang.service.UserService;
import modi.modurang.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@RequestBody SignupRequestDto signupRequest) {
        userService.saveUser(signupRequest);
        return ResponseEntity.ok(new SignupResponseDto("SIGNUP_SUCCESS", "회원가입이 성공적으로 완료되었습니다."));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequest) {
        try {
            User user = userService.authenticateUser(loginRequest.getStudentNumber(), loginRequest.getPassword());
            if (user != null) {
                String token = jwtUtil.generateToken(user.getUsername());
                return ResponseEntity.ok(new LoginResponseDto(token, "로그인 성공"));
            } else {
                return ResponseEntity.badRequest().body(new LoginResponseDto(null, "로그인 실패: 잘못된 사용자 이름 또는 비밀번호"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new LoginResponseDto(null, "로그인 실패: " + e.getMessage()));
        }
    }
}
