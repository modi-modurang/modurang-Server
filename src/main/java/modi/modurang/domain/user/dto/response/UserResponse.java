package modi.modurang.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import modi.modurang.domain.club.enums.Club;

@Data
@AllArgsConstructor
public class UserResponse {
    private Long id;

    private String username;

    private String studentNumber;

    private String email;

    private Club club;
}