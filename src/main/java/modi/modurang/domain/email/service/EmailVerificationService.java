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

@Service
@RequiredArgsConstructor
public class EmailVerificationService {

    private final EmailRepository emailRepository;
    private final EmailService emailService;

    @Transactional
    public void sendVerificationCode(String email) {
        if (emailRepository.findByEmailAndIsVerifiedTrue(email).isPresent()) {
            throw new CustomException(ErrorCode.ALREADY_VERIFIED_EMAIL);
        } else {
            String code = generateVerificationCode();
            LocalDateTime expirationDate = LocalDateTime.now().plusMinutes(5);

            Email emailVerification = Email.builder()
                    .email(email)
                    .verificationCode(code)
                    .expirationDate(expirationDate)
                    .isVerified(false)
                    .build();

            emailRepository.save(emailVerification);

            emailService.sendEmail(email, code);
        }
    }

    private String generateVerificationCode() {
        return String.format("%06d", new SecureRandom().nextInt(1_000_000));
    }

    @Transactional
    public void verifyCode(String email, String code) {
        Email verification = emailRepository.findByEmailAndVerificationCode(email, code).orElse(null);

        if (verification == null) {
            throw new CustomException(ErrorCode.EMAIL_NOT_FOUND);
        } else if (verification.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new CustomException(ErrorCode.EXPIRED_EMAIL);
        } else {
            verification.setVerified(true);
            verification.setVerificationCode(null);
            verification.setExpirationDate(null);
            emailRepository.save(verification);
        }
    }
}
