package modi.modurang.global.security.jwt.dto;

public record Jwt(
        String accessToken,
        String refreshToken
) {
}