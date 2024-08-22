package modi.modurang.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// 멤버 서비스 클래스
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;  // 멤버 레포지토리 주입
    private final PasswordEncoder passwordEncoder;  // 비밀번호 인코더 주입

    // 멤버 정보를 받아 저장하는 메소드
    public void memberSave(String username, String password, int studentNumber) {
        Member member = new Member();
        member.setUsername(username);
        member.setPassword(passwordEncoder.encode(password));  // 비밀번호를 암호화하여 저장
        member.setStudentNumber(studentNumber);
        memberRepository.save(member);
    }
}
