package modi.modurang.member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// 멤버 테이블과 매핑되는 엔티티 클래스
@Table(name = "member")
@Entity
@Getter
@Setter
@ToString
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;  // 회원 ID (자동 생성)

    @Column(nullable = false)
    private String username;  // 사용자 이름 (필수)

    @Column(nullable = false)
    private String password;  // 비밀번호 (필수)

    @Column(nullable = false, unique = true)
    private int studentNumber;  // 학번 (필수, 유니크)
}
