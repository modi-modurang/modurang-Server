package modi.modurang.domain.notice.controller;

import lombok.RequiredArgsConstructor;
import modi.modurang.domain.notice.dto.request.NoticeRequest;
import modi.modurang.domain.notice.service.NoticeService;
import modi.modurang.global.dto.response.CommonResponse;
import modi.modurang.global.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @PostMapping("/write")
    public ResponseEntity<CommonResponse> createNotice(@RequestBody NoticeRequest noticeRequest) {
        try {
            noticeService.createNotice(noticeRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(new CommonResponse("공지 등록 성공"));
        } catch (CustomException e) {
            return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(new CommonResponse("공지 등록 실패: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CommonResponse("공지 등록 실패: " + e.getMessage()));
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CommonResponse> updateNotice(@PathVariable Long id, @RequestBody NoticeRequest noticeRequest) {
        try {
            noticeService.updateNotice(id, noticeRequest);
            return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse("공지 수정 성공"));
        } catch (CustomException e) {
            return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(new CommonResponse("공지 수정 실패: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CommonResponse("공지 수정 실패: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> deleteNotice(@PathVariable Long id) {
        try {
            noticeService.deleteNotice(id);
            return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse("공지 삭제 성공"));
        } catch (CustomException e) {
            return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(new CommonResponse("공지 삭제 실패: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CommonResponse("공지 삭제 실패: " + e.getMessage()));
        }
    }
}
