package modi.modurang.auth;

import lombok.RequiredArgsConstructor;
import modi.modurang.dto.LoginRequestDto;
import modi.modurang.dto.LoginResponseDto;
import modi.modurang.dto.SignupDto;
import modi.modurang.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/signup")
    public void signup(@RequestBody SignupDto signupRequest) {
        userService.saveUser(signupRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequest) {
        try {
            LoginResponseDto response = userService.login(loginRequest.getStudentNumber(), loginRequest.getPassword());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new LoginResponseDto(null, "로그인 실패: " + e.getMessage()));
        }
    }
}
