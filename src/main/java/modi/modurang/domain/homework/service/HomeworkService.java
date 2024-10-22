package modi.modurang.domain.homework.service;

import modi.modurang.domain.homework.dto.request.HomeworkRequest;
import modi.modurang.global.security.details.CustomUserDetails;

public interface HomeworkService {

    void createHomework(HomeworkRequest request, CustomUserDetails CustomUserDetails);
}
