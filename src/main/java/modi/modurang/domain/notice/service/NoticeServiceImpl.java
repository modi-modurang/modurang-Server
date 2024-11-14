package modi.modurang.domain.notice.service;

import lombok.RequiredArgsConstructor;
import modi.modurang.domain.notice.dto.request.NoticeRequest;
import modi.modurang.domain.notice.entity.Notice;
import modi.modurang.domain.notice.error.NoticeError;
import modi.modurang.domain.notice.repository.NoticeRepository;
import modi.modurang.domain.user.entity.User;
import modi.modurang.domain.user.error.UserError;
import modi.modurang.domain.user.repository.UserRepository;
import modi.modurang.global.error.CustomException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public void createNotice(NoticeRequest noticeRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails userDetails) {
            String email = userDetails.getUsername();
            User user = userRepository.findByEmail(email).orElseThrow(() -> new CustomException(UserError.USER_NOT_FOUND));
            String username = user.getUsername();

            Notice notice = Notice.builder()
                    .title(noticeRequest.getTitle())
                    .content(noticeRequest.getContent())
                    .writer(username)
                    .isPinned(false)
                    .build();

            noticeRepository.save(notice);
        } else {
            throw new CustomException(UserError.USER_NOT_FOUND);
        }
    }


    @Transactional
    @Override
    public void deleteNotice(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails userDetails) {
            String email = userDetails.getUsername();
            User user = userRepository.findByEmail(email).orElseThrow(() -> new CustomException(UserError.USER_NOT_FOUND));

            Notice notice = noticeRepository.findById(id).orElseThrow(() -> new CustomException(NoticeError.NOTICE_NOT_FOUND));
            if (!notice.getWriter().equals(user.getUsername())) {
                throw new RuntimeException("권한이 없습니다.");
            }

            noticeRepository.delete(notice);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Notice getNoticeById(Long id) {
        return noticeRepository.findById(id).orElseThrow(() -> new CustomException(NoticeError.NOTICE_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Notice> getAllNotices() {
        return noticeRepository.findAll();
    }

    @Transactional
    @Override
    public void toggleNoticePin(Long id) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new CustomException(NoticeError.NOTICE_NOT_FOUND));

        // 현재 상태의 반대로 설정
        notice.setPinned(!notice.isPinned());

        noticeRepository.save(notice);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Notice> getPinnedNotices() {
        return noticeRepository.findByIsPinned(true);
    }
}
