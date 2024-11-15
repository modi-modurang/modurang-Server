package modi.modurang.domain.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ChangePasswordRequest {

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*?&]{8,}$", message = "비밀번호는 영어와 숫자를 포함하고 8자 이상이여야 합니다.")
    @NotBlank(message = "현재 비밀번호를 입력해 주세요.")
    private String currentPassword;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*?&]{8,}$", message = "비밀번호는 영어와 숫자를 포함하고 8자 이상이여야 합니다.")
    @NotBlank(message = "새 비밀번호를 입력해 주세요.")
    private String newPassword;
}
