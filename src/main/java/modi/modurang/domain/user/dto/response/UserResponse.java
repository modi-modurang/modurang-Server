package modi.modurang.domain.user.dto.response;

public record UserResponse(
    String username,
    String studentNumber,
    String email,
    String club
) {
}