package modi.modurang.domain.club.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {

    private String club;
    private String username;
    private String StudentNumber;
}
