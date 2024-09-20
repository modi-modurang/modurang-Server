package modi.modurang.domain.email.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String code;
}
