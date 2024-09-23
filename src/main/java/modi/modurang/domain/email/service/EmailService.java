package modi.modurang.domain.email.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import modi.modurang.domain.email.repository.EmailRepository;
import modi.modurang.global.config.MailConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender javaMailSender;
    private final MailConfig mailConfig;
    private final EmailRepository emailRepository;

    public void sendEmail(String email, String code) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            message.setFrom(mailConfig.getUsername());
            message.setRecipients(MimeMessage.RecipientType.TO, email);
            message.setSubject("모두랑 이메일 인증코드 : " + code);

            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(createMessageBodyPart(code));
            multipart.addBodyPart(createImageBodyPart());

            message.setContent(multipart);

            javaMailSender.send(message);
        } catch (MessagingException | IOException e) {
            logger.error("{}에게 인증 이메일 전송 실패: {}", email, e.getMessage());
        }
    }

    private MimeBodyPart createMessageBodyPart(String code) throws MessagingException {
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        String htmlContent = "<div style='text-align: center;'>"
                + "<img src='cid:image' style='width: 150px;'><br><br>"
                + "<p style='font-size: 16px; font-weight: bold;'>모두랑 이메일 인증을 위해 아래 코드를 입력하세요</p>"
                + "<h1 style='font-size: 48px; margin: 20px 0; letter-spacing: 10px;'>" + code + "</h1>"
                + "<p style='font-size: 14px;'>코드는 5분 후 만료됩니다.</p>"
                + "<p style='font-size: 10px; line-height: 1.6; margin: 20px 0;'>만약 이 요청을 본인이 하지 않았다면, 이 메일을 무시하셔도 됩니다.</p>"
                + "<div style='text-align: center; margin-top: 30px; font-size: 12px; color: #999;'>"
                + "© 2024 Modurang. All rights reserved."
                + "</div>"
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

    @Scheduled(cron = "0 0 * * * *")
    @Transactional
    public void deleteExpiredEmails() {
        emailRepository.deleteByExpirationDateBeforeAndIsVerifiedFalse(LocalDateTime.now());
    }
}
