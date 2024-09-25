package modi.modurang.domain.email.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailVerifyRequest {

    @Email(message = "올바른 이메일 형식을 입력하세요.")
    @NotBlank(message = "이메일은 필수 항목입니다.")
    private String email;

    @Pattern(regexp = "\\d{6}", message = "인증코드는 숫자 6자리로 구성되어야 합니다.")
    private String code;
}
