package modi.modurang.domain.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReissueResponse {

    private String accessToken;

    private String message;
}
