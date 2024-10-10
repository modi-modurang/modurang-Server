package modi.modurang.domain.notice.controller;

import lombok.RequiredArgsConstructor;
import modi.modurang.domain.notice.dto.request.NoticeRequest;
import modi.modurang.domain.notice.service.NoticeService;
import modi.modurang.global.common.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @PostMapping("/write")
    public ResponseEntity<BaseResponse> createNotice(@RequestBody NoticeRequest noticeRequest) {
        noticeService.createNotice(noticeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse("공지 등록 성공"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse> deleteNotice(@PathVariable Long id) {
        noticeService.deleteNotice(id);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse("공지 삭제 성공"));
    }
}
