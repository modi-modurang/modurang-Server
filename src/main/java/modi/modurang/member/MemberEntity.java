package modi.modurang.member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Table(name = "member")
@Entity
@Getter
@Setter
@ToString
public class MemberEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private int grade;
    @Column(nullable = false)
    private int classroom;
    @Column(nullable = false)
    private int number;
}
