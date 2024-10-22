package modi.modurang.domain.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReissueRequest {

    @NotBlank(message = "리프레쉬 토큰을 입력해 주세요.")
    private String refreshToken;
}
