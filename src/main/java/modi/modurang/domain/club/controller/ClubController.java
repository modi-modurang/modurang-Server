package modi.modurang.domain.club.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import modi.modurang.domain.club.dto.request.ClubRequest;
import modi.modurang.domain.club.service.ClubService;
import modi.modurang.global.dto.response.CommonResponse;
import modi.modurang.global.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/club")
@RequiredArgsConstructor
public class ClubController {

    private final ClubService clubService;

    @PostMapping("/join")
    public ResponseEntity<CommonResponse> join(@Valid @RequestBody ClubRequest request) {
        try {
            clubService.join(request);
            return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse("동아리 가입 성공"));
        } catch (CustomException e) {
            return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(new CommonResponse("동아리 가입 실패: " + e.getMessage()));
        }
    }

    @PostMapping("/modify")
    public ResponseEntity<CommonResponse> modify(@Valid @RequestBody ClubRequest request) {
        try {
            clubService.modify(request);
            return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse("동아리 수정 성공"));
        } catch (CustomException e) {
            return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(new CommonResponse("동아리 수정 실패: " + e.getMessage()));
        }
    }

    @PostMapping("/admin")
    public ResponseEntity<CommonResponse> admin(@Valid @RequestBody ClubRequest request) {
        try {
            clubService.admin(request);
            return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse("권한 부여 성공"));
        } catch (CustomException e) {
            return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(new CommonResponse("권한 부여 성공: " + e.getMessage()));
        }
    }
}
