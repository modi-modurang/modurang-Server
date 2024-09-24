package modi.modurang.domain.club.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import modi.modurang.domain.club.dto.request.AdminRequest;
import modi.modurang.domain.club.dto.request.JoinRequest;
import modi.modurang.domain.club.dto.request.ModifyRequest;
import modi.modurang.domain.club.dto.response.AdminResponse;
import modi.modurang.domain.club.dto.response.JoinResponse;
import modi.modurang.domain.club.dto.response.ModifyResponse;
import modi.modurang.domain.club.service.ClubService;
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
    public ResponseEntity<JoinResponse> join(@Valid @RequestBody JoinRequest request) {
        try {
            clubService.join(request);
            return ResponseEntity.status(HttpStatus.OK).body(new JoinResponse("동아리 가입 성공"));
        } catch (CustomException e) {
            return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(new JoinResponse("동아리 가입 실패: " + e.getMessage()));
        }
    }

    @PostMapping("/modify")
    public ResponseEntity<ModifyResponse> modify(@Valid @RequestBody ModifyRequest request) {
        try {
            clubService.modify(request);
            return ResponseEntity.status(HttpStatus.OK).body(new ModifyResponse("동아리 수정 성공"));
        } catch (CustomException e) {
            return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(new ModifyResponse("동아리 수정 실패: " + e.getMessage()));
        }
    }

    @PostMapping("/admin")
    public ResponseEntity<AdminResponse> admin(@Valid @RequestBody AdminRequest request) {
        try {
            clubService.admin(request);
            return ResponseEntity.status(HttpStatus.OK).body(new AdminResponse("권한 부여 성공"));
        } catch (CustomException e) {
            return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(new AdminResponse("권한 부여 성공: " + e.getMessage()));
        }
    }
}
