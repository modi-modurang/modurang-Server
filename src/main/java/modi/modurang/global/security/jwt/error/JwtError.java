package modi.modurang.global.security.jwt.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import modi.modurang.global.error.CustomError;

@Getter
@RequiredArgsConstructor
public enum JwtError implements CustomError {
    INVALID_TOKEN(401,"유효하지 않은 토큰입니다."),
    INVALID_REFRESH_TOKEN(401, "유효하지 않은 리프레쉬 토큰입니다."),
    UNSUPPORTED_TOKEN(400, "지원되지 않는 토큰 유형입니다."),
    EXPIRED_TOKEN(401, "토큰이 만료되었습니다."),
    MALFORMED_TOKEN(401, "잘못된 형식의 토큰입니다."),
    INVALID_TOKEN_TYPE(401, "유효하지 않은 토큰 타입입니다.");

    private final int status;
    private final String message;

    @Override
    public String getCode() {
        return name();
    }
}