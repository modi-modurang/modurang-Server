package modi.modurang.domain.club.service;

import modi.modurang.domain.club.dto.request.AdminRequest;
import modi.modurang.domain.club.dto.request.ClubRequest;
import modi.modurang.domain.club.dto.request.MemberRequest;
import modi.modurang.domain.user.dto.response.UserResponse;

import java.util.List;

public interface ClubService {

    List<UserResponse> clubMemberList(MemberRequest request);

    void joinClub(ClubRequest request);

    void modifyClub(ClubRequest request);

    void admin(AdminRequest request);
}
