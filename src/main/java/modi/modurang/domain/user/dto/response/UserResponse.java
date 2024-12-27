package modi.modurang.domain.user.dto.response;

import lombok.Builder;
import lombok.Data;
import modi.modurang.domain.club.enums.Club;
import modi.modurang.domain.user.entity.User;

@Data
@Builder
public class UserResponse {
    private Long id;

    private String username;

    private String studentNumber;

    private String email;

    private Club club;

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .studentNumber(user.getStudentNumber())
                .email(user.getEmail())
                .club(user.getClub())
                .build();
    }
}