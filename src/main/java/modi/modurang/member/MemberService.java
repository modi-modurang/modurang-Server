package modi.modurang.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// 서비스 클래스, 비즈니스 로직을 담당합니다.
@Service
@RequiredArgsConstructor // 모든 final 필드에 대한 생성자를 자동으로 생성합니다.
public class MemberService {

    private final MemberRepository memberRepository; // 데이터베이스 접근을 위한 레포지토리
    private final PasswordEncoder passwordEncoder; // 비밀번호 암호화를 위한 인코더

    // 회원 정보를 저장하는 메서드
    public void memberSave(String username, String password, int studentNumber) {
        Member member = new Member(); // 새로운 Member 객체 생성
        member.setUsername(username); // 사용자 이름 설정
        member.setPassword(passwordEncoder.encode(password)); // 비밀번호 암호화 및 설정
        member.setStudentNumber(studentNumber); // 학번 설정
        memberRepository.save(member); // 데이터베이스에 Member 객체 저장
    }
}
