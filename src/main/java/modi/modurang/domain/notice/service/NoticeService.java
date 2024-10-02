package modi.modurang.domain.notice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import modi.modurang.domain.notice.dto.request.NoticeRequest;
import modi.modurang.domain.notice.entity.Notice;
import modi.modurang.domain.notice.repository.NoticeRepository;
import modi.modurang.global.security.provider.JwtProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    public void createNotice(NoticeRequest noticeRequest) {
        Notice notice = new Notice();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            username = userDetails.getUsername();
        }

        notice.setTitle(noticeRequest.getTitle());
        notice.setContent(noticeRequest.getContent());
        notice.setAuthor(username);

        noticeRepository.save(notice);
    }


    @Transactional
    public void updateNotice(Long id, NoticeRequest noticeRequest, String token) {
        String currentUserId = jwtProvider.extractEmail(token);
        Notice notice = noticeRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("수정하시려는 공지사항을 찾을 수 없습니다."));
        if (!notice.getAuthor().equals(currentUserId)) {
            throw new RuntimeException("수정 권한이 없습니다.");
        }
        notice.setTitle(noticeRequest.getTitle());
        notice.setContent(noticeRequest.getContent());
        noticeRepository.save(notice);
    }

    @Transactional
    public void deleteNotice(Long id, String token) {
        String currentUserId = jwtProvider.extractEmail(token);
        Notice notice = noticeRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("삭제하시려는 공지사항을 찾을 수 없습니다."));
        if (!notice.getAuthor().equals(currentUserId)) {
            throw new RuntimeException("수정 권한이 없습니다.");
        }
        noticeRepository.deleteById(id);
    }
}
