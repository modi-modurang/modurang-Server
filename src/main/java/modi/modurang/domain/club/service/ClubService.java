package modi.modurang.domain.club.service;

import modi.modurang.domain.club.dto.request.AdminRequest;
import modi.modurang.domain.club.dto.request.ClubRequest;
import modi.modurang.domain.club.dto.request.MemberRequest;
import modi.modurang.domain.user.entity.User;

import java.util.List;

public interface ClubService {

    List<User> club(MemberRequest request);

    void join(ClubRequest request);

    void modify(ClubRequest request);

    void admin(AdminRequest request);
}
