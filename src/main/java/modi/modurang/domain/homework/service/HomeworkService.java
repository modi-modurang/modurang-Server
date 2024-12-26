package modi.modurang.domain.homework.service;

import modi.modurang.domain.homework.dto.request.HomeworkRequest;
import modi.modurang.domain.user.entity.User;
import modi.modurang.global.security.annotation.CurrentUser;

public interface HomeworkService {
    void createHomework(@CurrentUser User user, HomeworkRequest request);
}