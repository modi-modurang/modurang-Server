package modi.modurang.repository;

import modi.modurang.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<Email, String> {

    Email findByEmailAndVerificationCode(String email, String code);
}