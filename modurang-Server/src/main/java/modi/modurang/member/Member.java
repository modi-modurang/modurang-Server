package modi.modurang.member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity // 이 클래스가 JPA 엔티티임을 나타냅니다.
@Getter // 모든 필드에 대해 Getter 메서드를 자동으로 생성합니다.
@Setter // 모든 필드에 대해 Setter 메서드를 자동으로 생성합니다.
@ToString // 클래스의 toString 메서드를 자동으로 생성하여 객체의 문자열 표현을 제공합니다.
public class Member {

    @Id // 이 필드가 엔티티의 기본키임을 나타냅니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 값을 데이터베이스가 자동으로 생성합니다.
    private int id;

    @Column(nullable = false) // 이 컬럼은 null 값을 허용하지 않습니다.
    private String username;

    @Column(nullable = false) // 이 컬럼은 null 값을 허용하지 않습니다.
    private String password;

    @Column(nullable = false, unique = true) // 이 컬럼은 null 값을 허용하지 않으며, 유일한 값이어야 합니다.
    private int studentNumber;
}
