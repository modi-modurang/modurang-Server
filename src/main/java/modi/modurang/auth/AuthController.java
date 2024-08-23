package modi.modurang.auth;

import lombok.RequiredArgsConstructor;
import modi.modurang.dto.SignupRequestDto;
import modi.modurang.dto.SignupResponseDto;
import modi.modurang.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@RequestBody SignupRequestDto signupRequest) {
        try {
            userService.memberSave(signupRequest.getUsername(), signupRequest.getPassword(), signupRequest.getStudentNumber());
            return ResponseEntity.ok(new SignupResponseDto("회원가입 성공"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new SignupResponseDto("회원가입 실패: " + e.getMessage()));
        }
    }
}
