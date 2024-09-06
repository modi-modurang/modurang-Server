package modi.modurang.service;

import lombok.RequiredArgsConstructor;
import modi.modurang.dto.NoticeDto;
import modi.modurang.entity.Notice;
import modi.modurang.entity.User;
import modi.modurang.repository.NoticeRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public void saveNotice(NoticeDto noticeDto, User user) {
        Notice notice = new Notice();
        notice.setTitle(noticeDto.getTitle());
        notice.setContent(noticeDto.getContent());
        notice.setWriter(user.getUsername());
        noticeRepository.save(notice);
    }

    public void searchNotice(NoticeDto noticeDto, User user) {
        Notice notice = new Notice();
    }
}
