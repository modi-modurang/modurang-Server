package modi.modurang.domain.club.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminRequest {

    @Size(min = 2, max = 5, message = "이름은 2자 이상 5자 이하여야 합니다.")
    @NotBlank(message = "이름을 입력해 주세요.")
    private String username;

    @Pattern(regexp = "\\d{4}", message = "학번은 숫자 4자리로 구성되어야 합니다.")
    @NotBlank(message = "학번을 입력해 주세요.")
    private String studentNumber;
}
