package modi.modurang.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, max = 5)
    @Column(nullable = false, length = 5)
    private String username;

    @Size(min = 1, max = 100)
    @Column(nullable = false, length = 100)
    private String password;

    @Size(min = 4, max = 4)
    @Column(nullable = false, unique = true, length = 4)
    private String studentNumber;

    @Size(min = 1, max = 36)
    @Column(nullable = false, length = 36)
    private String email;
}
