package modi.modurang.domain.notice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import modi.modurang.domain.notice.dto.request.NoticeRequest;
import modi.modurang.domain.notice.dto.response.NoticeResponse;
import modi.modurang.domain.notice.service.NoticeService;
import modi.modurang.global.common.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "notices", description = "공지사항 API")
@RestController
@RequestMapping("/api/v1/notices")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @Operation(summary = "공지사항 작성")
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<BaseResponse<NoticeResponse>> createNotice(@Valid @RequestBody NoticeRequest noticeRequest) {
        noticeService.createNotice(noticeRequest);
        return BaseResponse.of(null, 201);
    }

    @Operation(summary = "공지사항 삭제")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<BaseResponse<Void>> deleteNotice(@PathVariable Long id) {
        noticeService.deleteNotice(id);
        return BaseResponse.of(null, 200);
    }

    @Operation(summary = "단일 공지사항 조회")
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<NoticeResponse>> getNotice(@PathVariable Long id) {
        return BaseResponse.of(noticeService.getNoticeById(id));
    }

    @Operation(summary = "전체 공지사항 조회")
    @GetMapping
    public ResponseEntity<BaseResponse<List<NoticeResponse>>> getAllNotices() {
        return BaseResponse.of(noticeService.getAllNotices());
    }

    @Operation(summary = "공지사항 고정/해제")
    @PatchMapping("/{id}/pin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> toggleNoticePin(@PathVariable Long id) {
        noticeService.toggleNoticePin(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "고정된 공지사항 조회")
    @GetMapping("/pinned")
    public ResponseEntity<BaseResponse<List<NoticeResponse>>> getPinnedNotices() {
        List<NoticeResponse> responses = noticeService.getPinnedNotices();
        return BaseResponse.of(responses, 200);
    }
}
