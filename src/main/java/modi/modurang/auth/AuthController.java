package modi.modurang.auth;

import lombok.RequiredArgsConstructor;
import modi.modurang.domain.User;
import modi.modurang.dto.LoginRequestDto;
import modi.modurang.dto.LoginResponseDto;
import modi.modurang.dto.SignupDto;
import modi.modurang.service.UserService;
import modi.modurang.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public void signup(@RequestBody SignupDto signupRequest) {
        userService.saveUser(signupRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequest) {
        try {
            User user = userService.authenticateUser(loginRequest.getStudentNumber(), loginRequest.getPassword());
            if (user != null) {
                String accessToken = jwtUtil.generateToken(user.getUsername());
                return ResponseEntity.ok(new LoginResponseDto(accessToken, "로그인 성공"));
            } else {
                return ResponseEntity.badRequest().body(new LoginResponseDto(null, "로그인 실패: 잘못된 사용자 이름 또는 비밀번호"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new LoginResponseDto(null, "로그인 실패: " + e.getMessage()));
        }
    }
}
