package modi.modurang.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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

    @Size(min = 1, max = 36)
    @Column(nullable = false, length = 36)
    private String email;

    @Size(min = 6, max = 6)
    @Column(nullable = false, length = 6)
    private String verificationCode;

    @Column(nullable = false)
    private LocalDateTime expirationDate;
}
