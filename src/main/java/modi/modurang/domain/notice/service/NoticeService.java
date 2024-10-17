package modi.modurang.domain.notice.service;

import modi.modurang.domain.notice.dto.request.NoticeRequest;
import modi.modurang.domain.notice.entity.Notice;

import java.util.List;

public interface NoticeService {

    void createNotice(NoticeRequest noticeRequest);

    void deleteNotice(Long id);

    Notice getNoticeById(Long id);

    List<Notice> getAllNotices();

    void pinNotice(Long id);

    void unpinNotice(Long id);

    List<Notice> getPinnedNotices();
}
