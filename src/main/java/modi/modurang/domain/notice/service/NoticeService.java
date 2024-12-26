package modi.modurang.domain.notice.service;

import modi.modurang.domain.notice.dto.request.NoticeRequest;
import modi.modurang.domain.notice.dto.response.NoticeResponse;
import modi.modurang.domain.user.entity.User;
import modi.modurang.global.security.annotation.CurrentUser;

import java.util.List;

public interface NoticeService {
    void createNotice(@CurrentUser User user, NoticeRequest noticeRequest);

    void deleteNotice(Long id);

    NoticeResponse getNoticeById(Long id);

    List<NoticeResponse> getAllNotices();

    void toggleNoticePin(Long id);
}