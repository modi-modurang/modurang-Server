package modi.modurang.domain.notice.service;

import lombok.RequiredArgsConstructor;
import modi.modurang.domain.notice.dto.request.NoticeRequest;
import modi.modurang.domain.user.entity.User;
import modi.modurang.entity.Notice;
import modi.modurang.domain.notice.repository.NoticeRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public void saveNotice(NoticeRequest request, User user) {
        Notice notice = new Notice();
        notice.setTitle(request.getTitle());
        notice.setContent(request.getContent());
        notice.setWriter(user.getUsername());
        noticeRepository.save(notice);
    }
}
