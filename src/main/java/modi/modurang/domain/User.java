package modi.modurang.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity // 이 클래스가 JPA 엔티티임을 나타내며, 데이터베이스 테이블과 매핑됩니다.
@Getter // 모든 필드에 대해 Getter 메서드를 자동으로 생성합니다.
@Setter // 모든 필드에 대해 Setter 메서드를 자동으로 생성합니다.
@ToString // 클래스의 toString 메서드를 자동으로 생성하여 객체의 문자열 표현을 제공합니다.
public class User {

    @Id // 이 필드가 엔티티의 기본키(primary key)임을 나타냅니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 값을 데이터베이스가 자동으로 생성(자동 증가)하도록 설정합니다.
    private int id;

    @Column(nullable = false, length = 5) // 이 컬럼은 null 값을 허용하지 않으며, 최대 길이는 5입니다.
    private String username; // 사용자의 이름을 저장하는 필드입니다.

    @Column(nullable = false, length = 12) // 이 컬럼은 null 값을 허용하지 않으며, 최대 길이는 12입니다.
    private String password; // 사용자의 비밀번호를 저장하는 필드입니다.

    @Column(nullable = false, unique = true, length = 4) // 이 컬럼은 null 값을 허용하지 않으며, 유일한 값이어야 하고, 최대 길이는 4입니다.
    private int studentNumber; // 사용자의 학번을 저장하는 필드입니다.
}
