package modi.modurang.domain.auth.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import modi.modurang.global.error.CustomError;

@Getter
@RequiredArgsConstructor
public enum AuthError implements CustomError {
    WRONG_PASSWORD(401, "잘못된 비밀번호입니다.");

    private final int status;
    private final String message;

    @Override
    public String getCode() {
        return name();
    }
}