package modi.modurang.domain.user.dto.response;

public record UserResponse(
        Long id,
        String username,
        String studentNumber,
        String email,
        modi.modurang.domain.club.enums.Club club
) {
}