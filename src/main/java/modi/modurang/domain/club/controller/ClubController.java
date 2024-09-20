package modi.modurang.domain.club.controller;

import lombok.RequiredArgsConstructor;
import modi.modurang.domain.club.dto.request.SignupRequest;
import modi.modurang.domain.club.service.ClubService;
import modi.modurang.global.exception.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/club")
@RequiredArgsConstructor
public class ClubController {

    private final ClubService clubService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(SignupRequest request) {
        try {
            clubService.signup(request);
            return ResponseEntity.ok("동아리 가입 성공");
        } catch (CustomException e) {
            return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body("동아리 가입 실패: " + e.getMessage());
        }
    }
}
