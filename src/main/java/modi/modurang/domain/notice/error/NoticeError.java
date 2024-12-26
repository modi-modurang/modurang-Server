package modi.modurang.domain.notice.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import modi.modurang.global.error.CustomError;

@Getter
@RequiredArgsConstructor
public enum NoticeError implements CustomError {
    NOTICE_NOT_FOUND(404, "공지사항을 찾을 수 없습니다.");

    private final int status;
    private final String message;

    @Override
    public String getCode() {
        return name();
    }
}