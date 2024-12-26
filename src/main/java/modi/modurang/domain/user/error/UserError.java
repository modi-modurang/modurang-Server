package modi.modurang.domain.user.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import modi.modurang.global.error.CustomError;

@Getter
@RequiredArgsConstructor
public enum UserError implements CustomError {
    HAS_STUDENTNUMBER(400, "존재하는 학번입니다."),
    USER_NOT_FOUND(404, "유저를 찾을 수 없습니다."),
    HAS_EMAIL(400, "이미 가입된 이메일입니다."),
    EXPIRED_EMAIL(400, "만료된 이메일입니다."),
    EMAIL_NOT_VERIFIED(400, "인증되지 않은 이메일입니다."),
    EMAIL_NOT_FOUND(400, "이메일을 찾을 수 없습니다."),
    ALREADY_VERIFIED_EMAIL(400, "이미 인증된 이메일입니다.");

    private final int status;
    private final String message;

    @Override
    public String getCode() {
        return name();
    }
}