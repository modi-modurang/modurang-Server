package modi.modurang.domain.email.dto.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequest {

    private String email;

    private String code;
}
