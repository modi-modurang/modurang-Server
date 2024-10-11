package modi.modurang.domain.notice.controller;

import lombok.RequiredArgsConstructor;
import modi.modurang.domain.notice.dto.request.NoticeRequest;
import modi.modurang.domain.notice.service.NoticeService;
import modi.modurang.global.common.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @PostMapping("/write")
    public ResponseEntity<BaseResponse<Void>> createNotice(@RequestBody NoticeRequest noticeRequest) {
        noticeService.createNotice(noticeRequest);
        return BaseResponse.of(null, 201);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse<Void>> deleteNotice(@PathVariable Long id) {
        noticeService.deleteNotice(id);
        return BaseResponse.of(null);
    }
}
