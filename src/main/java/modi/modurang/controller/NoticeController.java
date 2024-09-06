package modi.modurang.controller;

import lombok.RequiredArgsConstructor;
import modi.modurang.dto.NoticeDto;
import modi.modurang.entity.User;
import modi.modurang.service.NoticeService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("/write")
    public void write(@RequestBody NoticeDto noticeDto, @RequestBody User user) {
        noticeService.saveNotice(noticeDto, user);
    }
}
