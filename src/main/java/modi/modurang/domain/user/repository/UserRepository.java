package modi.modurang.domain.user.repository;

import lombok.NonNull;
import modi.modurang.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @NonNull
    @Override
    Optional<User> findById(@NonNull Long id);

    boolean existsByStudentNumber(String studentNumber);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameAndStudentNumber(String username, String studentNumber);
}
