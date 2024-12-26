package modi.modurang.domain.email.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import modi.modurang.global.common.BaseEntity;

import java.time.LocalDateTime;

@Table(name = "emails")
@Entity
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Email extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(length = 6)
    private String verificationCode;

    private LocalDateTime expirationDate;

    @Column(nullable = false)
    private boolean isVerified;
}