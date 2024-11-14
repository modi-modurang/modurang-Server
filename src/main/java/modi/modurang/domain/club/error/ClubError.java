package modi.modurang.domain.club.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import modi.modurang.global.error.CustomError;

@Getter
@RequiredArgsConstructor
public enum ClubError implements CustomError {

    ALREADY_JOINED_CLUB(409, "이미 해당 클럽에 소속되어 있습니다."),
    NO_MEMBERS_FOUND(404, "부원이 없습니다."),
    UNAUTHORIZED_CLUB_MEMBER(403, "해당 유저는 이 동아리의 멤버가 아닙니다.");

    private final int status;
    private final String message;

    @Override
    public String getCode() {
        return name();
    }
}
