package modi.modurang.service;

import lombok.RequiredArgsConstructor;
import modi.modurang.entity.Email;
import modi.modurang.exception.CustomException;
import modi.modurang.exception.ErrorCode;
import modi.modurang.repository.EmailRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmailVerificationService {

    private final EmailRepository emailRepository;
    private final EmailService emailService;

    public void sendVerificationCode(String email) {
        String code = generateVerificationCode();
        LocalDateTime expirationDate = LocalDateTime.now().plusMinutes(5);

        Email emailVerification = new Email();
        emailVerification.setEmail(email);
        emailVerification.setVerificationCode(code);
        emailVerification.setExpirationDate(expirationDate);

        emailRepository.save(emailVerification);

        emailService.sendVerificationCode(email, code);
    }

    private String generateVerificationCode() {
        SecureRandom random = new SecureRandom();
        return String.format("%6d", random.nextInt(1000000));
    }

    public void verifyCode(String email, String code) {
        Email verification = emailRepository.findByEmailAndVerificationCode(email, code);

        if (verification != null && verification.getExpirationDate().isAfter(LocalDateTime.now())) {
            emailRepository.delete(verification);
        } else if (verification != null && verification.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new CustomException(ErrorCode.INVALID_EMAIL);
        } else {
            throw new CustomException(ErrorCode.UNABLE_TO_SEND_EMAIL);
        }
    }
}
