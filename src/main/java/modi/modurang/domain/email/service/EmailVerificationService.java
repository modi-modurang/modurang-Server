package modi.modurang.domain.email.service;

public interface EmailVerificationService {
    void sendVerificationCode(String email);

    String generateVerificationCode();

    void verifyCode(String email, String code);
}