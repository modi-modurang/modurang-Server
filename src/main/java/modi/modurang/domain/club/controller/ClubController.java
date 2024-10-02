package modi.modurang.domain.club.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import modi.modurang.domain.club.dto.request.AdminRequest;
import modi.modurang.domain.club.dto.request.ClubRequest;
import modi.modurang.domain.club.service.ClubService;
import modi.modurang.global.common.BaseResponse;
import modi.modurang.global.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/club")
@RequiredArgsConstructor
public class ClubController {

    private final ClubService clubService;

    @PostMapping("/join")
    public ResponseEntity<BaseResponse> join(@Valid @RequestBody ClubRequest request) {
        try {
            clubService.join(request);
            return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse("동아리 가입 성공"));
        } catch (CustomException e) {
            return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(new BaseResponse("동아리 가입 실패: " + e.getMessage()));
        }
    }

    @PutMapping("/modify")
    public ResponseEntity<BaseResponse> modify(@Valid @RequestBody ClubRequest request) {
        try {
            clubService.modify(request);
            return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse("동아리 수정 성공"));
        } catch (CustomException e) {
            return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(new BaseResponse("동아리 수정 실패: " + e.getMessage()));
        }
    }

    @PostMapping("/admin")
    public ResponseEntity<BaseResponse> admin(@Valid @RequestBody AdminRequest request) {
        try {
            clubService.admin(request);
            return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse("권한 부여 성공"));
        } catch (CustomException e) {
            return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(new BaseResponse("권한 부여 실패: " + e.getMessage()));
        }
    }
}
