package modi.modurang.controller;

import lombok.RequiredArgsConstructor;
import modi.modurang.dto.RefreshResponse;
import modi.modurang.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class TokenController {

    private final UserService userService;

    @PostMapping("/refresh")
    public ResponseEntity<RefreshResponse> refresh(@RequestBody String refreshToken) {
        try {
            String newAccessToken = userService.refreshAccessToken(refreshToken);
            return ResponseEntity.ok(new RefreshResponse(newAccessToken, "토큰 갱신 성공"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new RefreshResponse(null, "토큰 갱신 실패: " + e.getMessage()));
        }
    }
}
