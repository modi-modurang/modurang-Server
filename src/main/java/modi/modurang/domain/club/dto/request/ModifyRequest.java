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
public class ModifyRequest {

    @NotBlank(message = "동아리는 필수 항목입나다.")
    private String club;

    @Size(min = 2, max = 5, message = "이름은 2자 이상 5자 이하여야 합니다.")
    @NotBlank(message = "이름은 필수 항목입니다.")
    private String username;

    @Pattern(regexp = "^[1-3][1-4](0[1-9]|1[0-9]|20)$", message = "학번은 4자리이고, 유효한 학번이어야 합니다.")
    @NotBlank(message = "학번은 필수 항목입니다.")
    private String studentNumber;
}
