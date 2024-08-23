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

@RestController // 이 클래스가 REST API의 컨트롤러임을 나타냅니다.
@RequestMapping("/auth") // '/auth' 경로와 관련된 요청을 처리합니다.
@RequiredArgsConstructor // final 필드에 대한 생성자를 자동으로 생성합니다.
public class AuthController {

    private final UserService userService; // 회원 관련 비즈니스 로직을 처리하는 서비스

    @PostMapping("/signup") // HTTP POST 요청을 '/auth/signup' 경로로 매핑합니다.
    public ResponseEntity<SignupResponseDto> signup(@RequestBody SignupRequestDto signupRequest) {
        try {
            // 회원가입 요청을 처리하고, 성공 시 회원 정보를 저장합니다.
            userService.memberSave(signupRequest.getUsername(), signupRequest.getPassword(), signupRequest.getStudentNumber());
            return ResponseEntity.ok(new SignupResponseDto("회원가입 성공")); // 회원가입 성공 메시지를 반환합니다.
        } catch (Exception e) {
            // 예외 발생 시 실패 메시지를 반환합니다.
            return ResponseEntity.badRequest().body(new SignupResponseDto("회원가입 실패: " + e.getMessage()));
        }
    }
}
