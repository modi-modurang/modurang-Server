package modi.modurang.domain.club.service;

import modi.modurang.domain.club.dto.request.AdminRequest;
import modi.modurang.domain.club.dto.request.ClubRequest;
import modi.modurang.domain.club.dto.request.MemberRequest;
import modi.modurang.domain.user.dto.response.UserResponse;
import modi.modurang.domain.user.entity.User;
import modi.modurang.global.security.annotation.CurrentUser;

import java.util.List;

public interface ClubService {
    List<UserResponse> clubMemberList(MemberRequest request);

    void joinClub(@CurrentUser User user, ClubRequest request);

    void modifyClub(@CurrentUser User user, ClubRequest request);

    void admin(AdminRequest request);
}