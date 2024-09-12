package modi.modurang.service;

import jakarta.transaction.Transactional;
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
    private static final int VERIFICATION_CODE_LENGTH = 6;
    private static final int VERIFICATION_CODE_UPPER_BOUND = 999999;
    private static final int VERIFICATION_CODE_LOWER_BOUND = 100000;
    private static final int EXPIRATION_MINUTES = 5;

    private final EmailRepository emailRepository;
    private final EmailService emailService;

    @Transactional
    public void sendVerificationCode(String email) {
        String code = generateVerificationCode();
        LocalDateTime expirationDate = LocalDateTime.now().plusMinutes(EXPIRATION_MINUTES);

        Email emailVerification = new Email();
        emailVerification.setEmail(email);
        emailVerification.setVerificationCode(code);
        emailVerification.setExpirationDate(expirationDate);

        emailRepository.save(emailVerification);

        emailService.sendVerificationCode(email, code);
    }

    private String generateVerificationCode() {
        SecureRandom random = new SecureRandom();
        int code = random.nextInt(VERIFICATION_CODE_UPPER_BOUND - VERIFICATION_CODE_LOWER_BOUND + 1) + VERIFICATION_CODE_LOWER_BOUND;
        return String.format("%0" + VERIFICATION_CODE_LENGTH + "d", code);
    }

    @Transactional
    public void verifyCode(String email, String code) {
        Email verification = emailRepository.findByEmailAndVerificationCode(email, code).orElse(null);

        if (verification == null) {
            throw new CustomException(ErrorCode.UNABLE_TO_SEND_EMAIL);
        }

        if (verification.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new CustomException(ErrorCode.INVALID_EMAIL);
        }

        emailRepository.delete(verification);
    }
}
