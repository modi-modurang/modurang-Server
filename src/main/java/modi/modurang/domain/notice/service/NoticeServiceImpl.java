package modi.modurang.domain.notice.service;

import lombok.RequiredArgsConstructor;
import modi.modurang.domain.notice.dto.request.NoticeRequest;
import modi.modurang.domain.notice.dto.response.NoticeResponse;
import modi.modurang.domain.notice.entity.Notice;
import modi.modurang.domain.notice.error.NoticeError;
import modi.modurang.domain.notice.repository.NoticeRepository;
import modi.modurang.domain.user.entity.User;
import modi.modurang.global.error.CustomException;
import modi.modurang.global.security.util.SecurityUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {
    private final NoticeRepository noticeRepository;
    private final SecurityUtil securityUtil;

    @Transactional
    @Override
    public void createNotice(NoticeRequest noticeRequest) {
        User user = securityUtil.currentUser();
        String username = user.getUsername();

        Notice notice = Notice.builder()
                .title(noticeRequest.getTitle())
                .content(noticeRequest.getContent())
                .writer(username)
                .isPinned(false)
                .build();

        noticeRepository.save(notice);
    }

    @Transactional
    @Override
    public void deleteNotice(Long id) {
        Notice notice = noticeRepository.findById(id).orElseThrow(() -> new CustomException(NoticeError.NOTICE_NOT_FOUND));
        noticeRepository.delete(notice);
    }

    @Transactional(readOnly = true)
    @Override
    public NoticeResponse getNoticeById(Long id) {
        Notice notice = noticeRepository.findById(id).orElseThrow(() -> new CustomException(NoticeError.NOTICE_NOT_FOUND));
        return convertToResponse(notice);
    }

    @Transactional(readOnly = true)
    @Override
    public List<NoticeResponse> getAllNotices() {
        return noticeRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void toggleNoticePin(Long id) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new CustomException(NoticeError.NOTICE_NOT_FOUND));

        notice.setPinned(!notice.isPinned());

        noticeRepository.save(notice);
    }

    @Transactional(readOnly = true)
    @Override
    public List<NoticeResponse> getPinnedNotices() {
        return noticeRepository.findByIsPinned(true).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private NoticeResponse convertToResponse(Notice notice) {
        return new NoticeResponse(
                notice.getTitle(),
                notice.getContent(),
                notice.getWriter()
        );
    }
}