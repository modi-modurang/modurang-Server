package modi.modurang.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    HAS_STUDENTNUMBER(HttpStatus.CONFLICT, "USER-001", "존재하는 학번입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER-002", "유저를 찾을 수 없습니다."),
    WRONG_PASSWORD(HttpStatus.UNAUTHORIZED, "USER-003", "잘못된 비밀번호입니다."),
    HAS_EMAIL(HttpStatus.CONFLICT, "USER-004", "이미 가입된 이메일입니다."),
    EXPIRED_EMAIL(HttpStatus.BAD_REQUEST, "USER-005", "만료된 이메일입니다."),
    EMAIL_NOT_VERIFIED(HttpStatus.BAD_REQUEST, "USER-006", "인증되지 않은 이메일입니다."),
    UNABLE_TO_SEND_EMAIL(HttpStatus.BAD_REQUEST, "USER-007", "이메일 인증을 실패했습니다."),
    ALREADY_VERIFIED_EMAIL(HttpStatus.BAD_REQUEST, "USER-008", "이미 인증된 이메일입니다."),
    TOO_MANY_REQUESTS(HttpStatus.TOO_MANY_REQUESTS, "USER-009", "너무 자주 요청하셨습니다."),

    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN-001", "유효하지 않은 토큰입니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN-002", "유효하지 않은 리프레쉬 토큰입니다."),
    UNSUPPORTED_TOKEN(HttpStatus.BAD_REQUEST, "TOKEN-003", "지원되지 않는 토큰 유형입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN-004", "토큰이 만료되었습니다."),
    MALFORMED_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN-005", "잘못된 형식의 토큰입니다."),

    ALREADY_JOINED_CLUB(HttpStatus.CONFLICT, "CLUB-001", "이미 해당 클럽에 소속되어 있습니다."),
    NO_MEMBERS_FOUND(HttpStatus.NOT_FOUND, "CLUB-002", "부원이 없습니다."),
    UNAUTHORIZED_CLUB_MEMBER(HttpStatus.FORBIDDEN, "CLUB-003", "해당 유저는 이 동아리의 멤버가 아닙니다."),

    NOTICE_NOT_FOUND(HttpStatus.NOT_FOUND, "NOTICE-001", "공지사항을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
