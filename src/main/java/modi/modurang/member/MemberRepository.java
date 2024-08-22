package modi.modurang.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Member에 대한 JpaRepository 인터페이스
public interface MemberRepository extends JpaRepository<Member, Long> {
    // 사용자 이름으로 멤버를 조회하는 메소드
    Optional<Member> findByUsername(String username);
}
