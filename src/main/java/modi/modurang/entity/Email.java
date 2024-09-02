package modi.modurang.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Email {

    @Id
    private String email;
    private String verificationCode;
    private LocalDateTime expirationDate;
}
