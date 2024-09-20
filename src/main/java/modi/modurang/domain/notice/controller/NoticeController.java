package modi.modurang.domain.notice.controller;

import lombok.RequiredArgsConstructor;
import modi.modurang.domain.notice.dto.request.NoticeRequest;
import modi.modurang.domain.notice.service.NoticeService;
import modi.modurang.domain.user.entity.User;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("/write")
    public void write(@RequestBody NoticeRequest request, @RequestBody User user) {
        noticeService.saveNotice(request, user);
    }
}
