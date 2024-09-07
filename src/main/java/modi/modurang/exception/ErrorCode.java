package modi.modurang.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON-001", "서버 내부 오류입니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON-002", "잘못된 요청입니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON-003", "접근이 거부되었습니다."),

    HAS_STUDENTNUMBER(HttpStatus.CONFLICT, "ACCOUNT-001", "존재하는 학번입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "ACCOUNT-002", "사용자를 찾을 수 없습니다."),
    INVALID_EMAIL(HttpStatus.BAD_REQUEST, "ACCOUNT-003", "만료된 이메일입니다."),
    UNABLE_TO_SEND_EMAIL(HttpStatus.INTERNAL_SERVER_ERROR, "ACCOUNT-004", "이메일 인증을 실패했습니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "ACCOUNT-005", "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "ACCOUNT-006", "만료된 토큰입니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "ACCOUNT-007", "잘못된 비밀번호입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
