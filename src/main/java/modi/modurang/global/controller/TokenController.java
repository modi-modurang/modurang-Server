package modi.modurang.global.controller;

import lombok.RequiredArgsConstructor;
import modi.modurang.domain.user.dto.RefreshResponseDto;
import modi.modurang.domain.user.service.UserService;
import modi.modurang.global.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class TokenController {

    private final UserService userService;

    @PostMapping("/refresh")
    public ResponseEntity<RefreshResponseDto> refresh(@RequestBody String refreshToken) {
        try {
            String newAccessToken = userService.refreshAccessToken(refreshToken);
            return ResponseEntity.ok(new RefreshResponseDto(newAccessToken, "토큰 갱신 성공"));
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new RefreshResponseDto(null, "토큰 갱신 실패: " + e.getMessage()));
        }
    }
}
