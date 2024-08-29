package modi.modurang.repository;

import modi.modurang.domain.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailVerificationRepository extends JpaRepository<EmailVerification, Long> {
    EmailVerification findByEmailAndVerificationCode(String email, String code);
}