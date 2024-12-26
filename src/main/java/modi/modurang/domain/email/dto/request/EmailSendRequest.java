package modi.modurang.domain.email.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailSendRequest {
    @Email(message = "올바른 이메일 형식을 입력하세요.")
    @NotBlank(message = "이메일을 입력해 주세요.")
    private String email;
}