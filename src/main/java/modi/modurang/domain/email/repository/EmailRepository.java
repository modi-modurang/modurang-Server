package modi.modurang.domain.email.repository;

import modi.modurang.domain.email.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailRepository extends JpaRepository<Email, Long> {

    Optional<Email> findByEmailAndVerificationCode(String email, String code);
}