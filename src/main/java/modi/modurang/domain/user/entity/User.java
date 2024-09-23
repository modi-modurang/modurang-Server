package modi.modurang.domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import modi.modurang.domain.user.enums.UserRole;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String studentNumber;

    @Column(unique = true, nullable = false)
    private String email;

    private String club;

    @Enumerated(EnumType.STRING)
    private UserRole role;
}
