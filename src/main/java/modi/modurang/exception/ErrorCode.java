package modi.modurang.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    HAS_STUDENTNUMBER(HttpStatus.CONFLICT, "ACCOUNT-001", "존재하는 학번입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "ACCOUNT-002", "사용자를 찾을 수 없습니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "ACCOUNT-003", "유효하지 않은 토큰입니다."),
    UNABLE_TO_SEND_EMAIL(HttpStatus.UNAUTHORIZED, "ACCOUNT-004", "이메일 인증을 실패했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
