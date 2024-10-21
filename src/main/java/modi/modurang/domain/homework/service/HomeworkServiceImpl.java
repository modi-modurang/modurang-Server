package modi.modurang.domain.homework.service;

import lombok.RequiredArgsConstructor;
import modi.modurang.domain.homework.dto.request.HomeworkRequest;
import modi.modurang.domain.homework.entity.Homework;
import modi.modurang.domain.homework.repository.HomeworkRepository;
import modi.modurang.domain.user.entity.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HomeworkServiceImpl implements HomeworkService {

    private final HomeworkRepository homeworkRepository;

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public void createHomework(HomeworkRequest request) {

        for (User user : request.getUserId()) {
            Homework homework = Homework.builder()
                    .title(request.getTitle())
                    .content(request.getContent())
                    .deadline(request.getDeadline())
                    .isCompleted(false)
                    .user(user)
                    .build();

            homeworkRepository.save(homework);
        }
    }
}
