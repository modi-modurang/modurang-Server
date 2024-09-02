package modi.modurang.repository;

import modi.modurang.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
<<<<<<< HEAD
    Optional<User> findByStudentNumber(String studentNumber);

    boolean existsByStudentNumber(String studentNumber);
=======

    // username을 기준으로 Member를 찾는 메서드 정의
    // 이 메서드는 데이터베이스에서 username이 일치하는 Member를 조회합니다.
    // 결과는 Optional로 반환되어, 조회된 결과가 없을 경우를 처리할 수 있습니다.
    Optional<User> findByUsername(String username);
>>>>>>> feature/announcements
}
