package modi.modurang.domain.notice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import modi.modurang.domain.notice.dto.request.NoticeRequest;
import modi.modurang.domain.notice.entity.Notice;
import modi.modurang.domain.notice.service.NoticeService;
import modi.modurang.global.common.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "공지", description = "Notice")
@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @Operation(summary = "공지 작성")
    @PostMapping("/write")
    public ResponseEntity<BaseResponse<Void>> createNotice(@RequestBody NoticeRequest noticeRequest) {
        noticeService.createNotice(noticeRequest);
        return BaseResponse.of(null, 201);
    }

    @Operation(summary = "공지 삭제")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse<Void>> deleteNotice(@PathVariable Long id) {
        noticeService.deleteNotice(id);
        return BaseResponse.of(null);
    }

    @Operation(summary = "단일 공지 조회")
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<Notice>> getNoticeById(@PathVariable Long id) {
        return BaseResponse.of(noticeService.getNoticeById(id));
    }

    @Operation(summary = "전체 공지 조회")
    @GetMapping("")
    public ResponseEntity<BaseResponse<List<Notice>>> getAllNotices() {
        return BaseResponse.of(noticeService.getAllNotices());
    }

    @Operation(summary = "공지 고정")
    @PostMapping("/{id}/pin")
    public ResponseEntity<BaseResponse<Void>> pinNotice(@PathVariable Long id) {
        noticeService.pinNotice(id);
        return BaseResponse.of(null);
    }

    @Operation(summary = "공지 고정 해제")
    @PostMapping("/{id}/unpin")
    public ResponseEntity<BaseResponse<Void>> unpinNotice(@PathVariable Long id) {
        noticeService.unpinNotice(id);
        return BaseResponse.of(null);
    }

    @Operation(summary = "고정 공지 조회")
    @GetMapping("/pinned")
    public ResponseEntity<BaseResponse<List<Notice>>> getPinnedNotices() {
        return BaseResponse.of(noticeService.getPinnedNotices());
    }
}
