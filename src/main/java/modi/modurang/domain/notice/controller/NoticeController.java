package modi.modurang.domain.notice.controller;

import lombok.RequiredArgsConstructor;
import modi.modurang.domain.notice.dto.NoticeDTO;
import modi.modurang.domain.notice.entity.Notice;
import modi.modurang.domain.notice.service.NoticeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("/")
    public ResponseEntity<List<NoticeDTO>> getAllNotices() {
        List<NoticeDTO> newNotice = noticeService.getAllNotices();
        return ResponseEntity.ok(newNotice);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoticeDTO> getNoticeById(@PathVariable Long id) {
        NoticeDTO newNotice = noticeService.getNoticeById(id);
        return ResponseEntity.ok(newNotice);
    }

    @PostMapping("/")
    public ResponseEntity<Notice> createNotice(@RequestBody NoticeDTO noticeDTO) {
        Notice newNotice = noticeService.createNotice(noticeDTO);
        return ResponseEntity.ok(newNotice);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Notice> updateNotice(@PathVariable Long id,
                                               @RequestBody NoticeDTO noticeDTO, @RequestHeader("Authorization") String token) {
        Notice newNotice = noticeService.updateNotice(id, noticeDTO, token);
        return ResponseEntity.ok(newNotice);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Notice> deleteNotice(@PathVariable Long id,
                                               @RequestHeader("Authorization") String token) {
        noticeService.deleteNotice(id, token); {
            return ResponseEntity.noContent().build();
        }
    }
}
