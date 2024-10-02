package modi.modurang.domain.notice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import modi.modurang.domain.notice.dto.request.NoticeRequest;
import modi.modurang.domain.notice.entity.Notice;
import modi.modurang.domain.notice.repository.NoticeRepository;
import modi.modurang.domain.user.entity.User;
import modi.modurang.domain.user.repository.UserRepository;
import modi.modurang.global.exception.CustomException;
import modi.modurang.global.exception.ErrorCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createNotice(NoticeRequest noticeRequest) {
        Notice notice = new Notice();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof UserDetails userDetails) {
            String email = userDetails.getUsername();

            User user = userRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
            String username = user.getUsername();

            notice.setTitle(noticeRequest.getTitle());
            notice.setContent(noticeRequest.getContent());
            notice.setWriter(username);

            noticeRepository.save(notice);
        } else {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
    }


    @Transactional
    public void updateNotice(Long id, NoticeRequest noticeRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof UserDetails userDetails) {
            String email = userDetails.getUsername();
            User user = userRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
            Notice notice = noticeRepository.findById(id).orElseThrow(() -> new RuntimeException("수정하시려는 공지사항을 찾을 수 없습니다."));
            if (!notice.getWriter().equals(user.getUsername())) {
                throw new RuntimeException("수정 권한이 없습니다.");
            } else {
                notice.setTitle(noticeRequest.getTitle());
                notice.setContent(noticeRequest.getContent());
                noticeRepository.save(notice);
            }
        }
    }

    @Transactional
    public void deleteNotice(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof UserDetails userDetails) {
            String email = userDetails.getUsername();
            User user = userRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
            Notice notice = noticeRepository.findById(id).orElseThrow(() -> new RuntimeException("삭제하시려는 공지사항을 찾을 수 없습니다."));
            if (!notice.getWriter().equals(user.getUsername())) {
                throw new RuntimeException("수정 권한이 없습니다.");
            } else {
                noticeRepository.delete(notice);
            }
        }
    }
}
