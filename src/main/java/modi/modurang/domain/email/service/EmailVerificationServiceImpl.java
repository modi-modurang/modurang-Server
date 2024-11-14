package modi.modurang.domain.email.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import modi.modurang.domain.email.entity.Email;
import modi.modurang.domain.email.repository.EmailRepository;
import modi.modurang.domain.user.error.UserError;
import modi.modurang.global.error.CustomException;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmailVerificationServiceImpl implements EmailVerificationService {

    private final EmailRepository emailRepository;
    private final EmailService emailService;

    @Transactional
    @Override
    public void sendVerificationCode(String email) {
        if (emailRepository.findByEmailAndIsVerifiedTrue(email).isPresent()) {
            throw new CustomException(UserError.ALREADY_VERIFIED_EMAIL);
        }

        String code = generateVerificationCode();
        LocalDateTime expirationDate = LocalDateTime.now().plusMinutes(5);

        Email emailVerification = emailRepository.findByEmail(email)
                .map(existing -> {
                    existing.setVerificationCode(code);
                    existing.setExpirationDate(expirationDate);
                    return existing;
                })
                .orElse(Email.builder()
                        .email(email)
                        .verificationCode(code)
                        .expirationDate(expirationDate)
                        .isVerified(false)
                        .build());

        emailRepository.save(emailVerification);
        emailService.sendEmail(email, code);
    }

    @Override
    public String generateVerificationCode() {
        return String.format("%06d", new SecureRandom().nextInt(1_000_000));
    }

    @Transactional
    @Override
    public void verifyCode(String email, String code) {
        Email verification = emailRepository.findByEmailAndVerificationCode(email, code).orElse(null);

        if (verification == null) {
            throw new CustomException(UserError.EMAIL_NOT_FOUND);
        } else if (verification.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new CustomException(UserError.EXPIRED_EMAIL);
        } else {
            verification.setVerified(true);
            verification.setVerificationCode(null);
            verification.setExpirationDate(null);
            emailRepository.save(verification);
        }
    }
}
