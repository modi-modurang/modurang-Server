package modi.modurang.repository;

import modi.modurang.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    Optional<Notice> findByTitle(String title);

    Optional<Notice> findByWriter(String writer);
}
