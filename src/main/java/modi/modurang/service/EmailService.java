package modi.modurang.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import lombok.RequiredArgsConstructor;
import modi.modurang.config.EmailConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender javaMailSender;
    private final EmailConfig emailConfig;

    public void sendVerificationCode(String toEmail, String code) {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(emailConfig.getUsername());
            message.setRecipients(MimeMessage.RecipientType.TO, toEmail);
            message.setSubject("모두랑 이메일 인증코드 : " + code);

            MimeMultipart multipart = createMultipart(code);

            message.setContent(multipart);

            javaMailSender.send(message);
        } catch (MessagingException | IOException e) {
            logger.error("Failed to send verification email to {}: {}", toEmail, e.getMessage());
        }
    }

    private MimeMultipart createMultipart(String code) throws MessagingException, IOException {
        MimeMultipart multipart = new MimeMultipart();

        MimeBodyPart messageBodyPart = createMessageBodyPart(code);
        MimeBodyPart imagePart = createImageBodyPart();

        multipart.addBodyPart(messageBodyPart);
        multipart.addBodyPart(imagePart);

        return multipart;
    }

    private MimeBodyPart createMessageBodyPart(String code) throws MessagingException {
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        String htmlContent = "<div style='text-align: center;'>"
                + "<img src='cid:image' style='width: 150px;'><br><br>"
                + "<p style='font-size: 16px; font-weight: bold;'>모두랑 이메일 인증을 위해 아래 코드를 입력하세요</p>"
                + "<h1 style='font-size: 48px; margin: 20px 0; letter-spacing: 10px;'>" + code + "</h1>"
                + "<p style='font-size: 14px;'>코드는 1분 후 만료됩니다.</p>"
                + "</div>";
        messageBodyPart.setContent(htmlContent, "text/html; charset=UTF-8");
        return messageBodyPart;
    }

    private MimeBodyPart createImageBodyPart() throws MessagingException, IOException {
        MimeBodyPart imagePart = new MimeBodyPart();
        ClassPathResource resource = new ClassPathResource("static/모두랑 인증코드 이미지.png");
        imagePart.attachFile(resource.getFile());
        imagePart.setContentID("<image>");
        imagePart.setDisposition(MimeBodyPart.INLINE);
        return imagePart;
    }
}
