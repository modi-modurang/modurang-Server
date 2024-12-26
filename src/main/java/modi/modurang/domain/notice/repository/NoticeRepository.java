package modi.modurang.domain.notice.repository;

import modi.modurang.domain.notice.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findByIsPinned(boolean isPinned);
}