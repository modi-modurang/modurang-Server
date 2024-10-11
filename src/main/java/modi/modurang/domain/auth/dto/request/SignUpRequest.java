package modi.modurang.domain.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpRequest {

    @Size(min = 2, max = 5, message = "이름은 2자 이상 5자 이하여야 합니다.")
    @NotBlank(message = "이름은 필수 항목입니다.")
    private String username;

    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하여야 합니다.")
    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    private String password;

    @Pattern(regexp = "\\d{4}", message = "학번은 숫자 4자리로 구성되어야 합니다.")
    @NotBlank(message = "학번은 필수 항목입니다.")
    private String studentNumber;

    @Email(message = "올바른 이메일 형식을 입력하세요.")
    @NotBlank(message = "이메일은 필수 항목입니다.")
    private String email;
}
