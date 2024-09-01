package modi.modurang.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender javaMailSender;
    private static final String SENDER_EMAIL = "dongwhilove09@gmail.com";

    public void sendVerificationCode(String toEmail, String code) {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(SENDER_EMAIL);
            message.setRecipients(MimeMessage.RecipientType.TO, toEmail);
            message.setSubject("이메일 인증");

            String body = "<h3>이메일 인증을 위해 아래 코드를 입력하세요:</h3>";
            body += "<h1>" + code + "</h1>";
            body += "<h3>감사합니다.</h3>";
            message.setContent(body, "text/html; charset=UTF-8");
        } catch (MessagingException e) {
            logger.error("Failed to send verification email to {}", toEmail, e);
        }

        javaMailSender.send(message);
    }
}
