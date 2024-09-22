package modi.modurang.domain.email.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import modi.modurang.domain.email.entity.Email;
import modi.modurang.domain.email.repository.EmailRepository;
import modi.modurang.global.exception.CustomException;
import modi.modurang.global.exception.ErrorCode;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Random;

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
        if (!isValidEmail(email)) {
            throw new CustomException(ErrorCode.INVALID_EMAIL);
        }

        String code = generateVerificationCode();
        LocalDateTime expirationDate = LocalDateTime.now().plusMinutes(EXPIRATION_MINUTES);

        Email emailVerification = new Email();
        emailVerification.setEmail(email);
        emailVerification.setVerificationCode(code);
        emailVerification.setExpirationDate(expirationDate);
        emailVerification.setVerified(false);

        emailRepository.save(emailVerification);

        emailService.sendEmail(email, code);
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        return email != null && !email.isBlank() && email.matches(emailRegex);
    }

    private String generateVerificationCode() {
        SecureRandom random = new SecureRandom();
        int code = random.nextInt(VERIFICATION_CODE_UPPER_BOUND - VERIFICATION_CODE_LOWER_BOUND + 1) + VERIFICATION_CODE_LOWER_BOUND;
        return String.format("%0" + VERIFICATION_CODE_LENGTH + "d", code);
    }

    private String generateEmailCode() {
        String chars = "abcdefghigklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder code = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 20; i++) {
            char c = chars.charAt(random.nextInt(chars.length()));

            code.append(c);
        }

        return code.toString();
    }

    @Transactional
    public void verifyCode(String email, String code) {
        Email verification = emailRepository.findByEmailAndVerificationCode(email, code).orElse(null);

        if (verification == null) {
            throw new CustomException(ErrorCode.UNABLE_TO_SEND_EMAIL);
        }

        if (verification.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new CustomException(ErrorCode.EXPIRED_EMAIL);
        }

        verification.setVerified(true);
        emailRepository.save(verification);
    }
}
