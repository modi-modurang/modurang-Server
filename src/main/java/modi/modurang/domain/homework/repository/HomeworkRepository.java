package modi.modurang.domain.homework.repository;

import modi.modurang.domain.homework.entity.Homework;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomeworkRepository extends JpaRepository<Homework, Long> {
}
