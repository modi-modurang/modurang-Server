package modi.modurang.repository;

import modi.modurang.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByStudentNumber(String studentNumber);

    boolean existsByStudentNumber(String studentNumber);
}
