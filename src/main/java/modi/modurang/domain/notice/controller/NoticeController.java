package modi.modurang.domain.notice.controller;

import lombok.RequiredArgsConstructor;
import modi.modurang.domain.notice.dto.request.NoticeRequest;
import modi.modurang.domain.notice.entity.Notice;
import modi.modurang.domain.notice.service.NoticeService;
import modi.modurang.global.dto.response.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @PostMapping("/")
    public ResponseEntity<CommonResponse> createNotice(@RequestBody NoticeRequest noticeDTO) {
        noticeService.createNotice(noticeDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse("공지 등록 성공"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse> updateNotice(@PathVariable Long id,
                                                       @RequestBody NoticeRequest noticeRequest, @RequestHeader("Authorization") String token) {
        noticeService.updateNotice(id, noticeRequest, token);
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse("공지 수정 성공"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> deleteNotice(@PathVariable Long id,
                                               @RequestHeader("Authorization") String token) {
        noticeService.deleteNotice(id, token);

        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse("공지 삭제 성공"));
    }
}
