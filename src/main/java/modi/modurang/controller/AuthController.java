package modi.modurang.controller;

import lombok.RequiredArgsConstructor;
import modi.modurang.dto.LoginResponse;
import modi.modurang.dto.SignUpRequest;
import modi.modurang.service.UserService;
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
    public ResponseEntity<String> signup(@RequestBody SignUpRequest signUpRequest) {
        try {
            userService.saveUser(signUpRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원가입 실패: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestParam("studentNumber") String studentNumber,
                                               @RequestParam("password") String password) {
        try {
            LoginResponse loginResponse = userService.login(studentNumber, password);
            return ResponseEntity.ok(loginResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse(null, null, "로그인 실패: " + e.getMessage()));
        }
    }
}
