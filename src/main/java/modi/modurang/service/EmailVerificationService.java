package modi.modurang.service;

import lombok.RequiredArgsConstructor;
import modi.modurang.domain.EmailVerification;
import modi.modurang.repository.EmailVerificationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmailVerificationService {

    private final EmailVerificationRepository emailVerificationRepository;
    private final EmailService emailService;

    public void sendVerificationCode(String email) {
        String code = generateVerificationCode();
        LocalDateTime expirationDate = LocalDateTime.now().plusMinutes(5);

        EmailVerification emailVerification = new EmailVerification();
        emailVerification.setEmail(email);
        emailVerification.setVerificationCode(code);
        emailVerification.setExpirationDate(expirationDate);
        emailVerification.setVerified(false);

        emailVerificationRepository.save(emailVerification);

        emailService.sendVerificationCode(email, code);
    }

    private String generateVerificationCode() {
        return String.format("%06d", (int)(Math.random() * 1000000));
    }

    public boolean verifyCode(String email, String code) {
        EmailVerification verification = emailVerificationRepository.findByEmailAndVerificationCode(email, code);

        if (verification != null && !verification.isVerified() && verification.getExpirationDate().isAfter(LocalDateTime.now())) {
            verification.setVerified(true);
            emailVerificationRepository.save(verification);
            return true;
        }
        return false;
    }
}
