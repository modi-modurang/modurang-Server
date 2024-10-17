package modi.modurang.domain.club.service;

import modi.modurang.domain.club.dto.request.AdminRequest;
import modi.modurang.domain.club.dto.request.ClubRequest;

public interface ClubService {

    void join(ClubRequest request);

    void modify(ClubRequest request);

    void admin(AdminRequest request);
}
