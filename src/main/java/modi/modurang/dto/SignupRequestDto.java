package modi.modurang.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // 클래스의 모든 필드에 대해 Getter, Setter, toString, equals, hashCode 메서드를 자동 생성합니다.
@AllArgsConstructor // 모든 필드를 파라미터로 받는 생성자를 자동으로 생성합니다.
@NoArgsConstructor // 파라미터가 없는 기본 생성자를 자동으로 생성합니다.
public class SignupRequestDto {
    private String username; // 사용자가 입력한 사용자 이름
    private String password; // 사용자가 입력한 비밀번호
    private int studentNumber; // 사용자가 입력한 학번
}
