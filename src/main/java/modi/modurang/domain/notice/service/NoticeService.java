package modi.modurang.domain.notice.service;

import jakarta.transaction.Transactional;
import modi.modurang.domain.notice.dto.NoticeDTO;
import modi.modurang.domain.notice.entity.Notice;
import modi.modurang.domain.notice.repository.NoticeRepository;
import modi.modurang.global.security.provider.JwtProvider;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final JwtProvider jwtProvider;

    public NoticeService(NoticeRepository noticeRepository, JwtProvider jwtProvider) {
        this.noticeRepository = noticeRepository;
        this.jwtProvider = jwtProvider;
    }

    public List<NoticeDTO> getAllNotices() {
        return noticeRepository
                .findAll()
                .stream()
                .map(n -> new NoticeDTO(n.getId(), n.getTitle(), n.getContent(), n.getAuthor()))
                .collect(Collectors.toList());

    }

    public NoticeDTO getNoticeById(Long id) {
        Optional<Notice> newNotice = noticeRepository.findById(id);
        return newNotice
                .map(n -> new NoticeDTO(n.getId(), n.getTitle(), n.getContent(), n.getAuthor()))
                .orElseThrow(() -> new RuntimeException("공지사항을 찾을 수 없습니다."));
    }

    public Notice createNotice(NoticeDTO noticeDTO) {
        Notice notice = new Notice();
        Notice newNotice = noticeRepository.save(notice);
        newNotice.setId(noticeDTO.getId());
        newNotice.setTitle(noticeDTO.getTitle());
        newNotice.setContent(noticeDTO.getContent());
        return noticeRepository.save(newNotice);
    }

    @Transactional
    public Notice updateNotice(Long id, NoticeDTO noticeDTO, String token) {
        String currentUserId = jwtProvider.extractEmail(token);
        Notice newNotice = noticeRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("수정하시려는 공지사항을 찾을 수 없습니다."));
        if (!newNotice.getAuthor().equals(currentUserId)) {
            throw new RuntimeException("수정 권한이 없습니다.");
        }
        newNotice.setTitle(noticeDTO.getTitle());
        newNotice.setContent(noticeDTO.getContent());
        return noticeRepository.save(newNotice);
    }

    @Transactional
    public void deleteNotice(Long id, String token) {
        String currentUserId = jwtProvider.extractEmail(token);
        Notice newNotice = noticeRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("삭제하시려는 공지사항을 찾을 수 없습니다."));
        if (!newNotice.getAuthor().equals(currentUserId)) {
            throw new RuntimeException("수정 권한이 없습니다.");
        }
        noticeRepository.deleteById(id);
    }
}
