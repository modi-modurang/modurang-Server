package modi.modurang.domain.user.repository;

import modi.modurang.domain.club.enums.Club;
import modi.modurang.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByStudentNumber(String studentNumber);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameAndStudentNumber(String username, String studentNumber);

    Optional<User> findAllByClub(Club club);
}
