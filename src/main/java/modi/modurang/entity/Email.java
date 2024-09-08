package modi.modurang.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 36)
    private String email;

    @Column(nullable = false, length = 6)
    private String verificationCode;

    @Column(nullable = false)
    private LocalDateTime expirationDate;
}
