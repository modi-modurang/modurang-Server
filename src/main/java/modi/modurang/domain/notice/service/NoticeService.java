package modi.modurang.domain.notice.service;

import modi.modurang.domain.notice.dto.request.NoticeRequest;
import modi.modurang.domain.notice.dto.response.NoticeResponse;
import java.util.List;

public interface NoticeService {
    void createNotice(NoticeRequest noticeRequest);

    void deleteNotice(Long id);

    NoticeResponse getNoticeById(Long id);

    List<NoticeResponse> getAllNotices();

    void toggleNoticePin(Long id);

    List<NoticeResponse> getPinnedNotices();
}