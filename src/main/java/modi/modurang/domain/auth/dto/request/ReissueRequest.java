package modi.modurang.domain.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReissueRequest {

    @NotBlank(message = "리프레쉬 토큰은 필수 항목입니다.")
    private String refreshToken;
}
