package modi.modurang.domain.homework.service;

import lombok.RequiredArgsConstructor;
import modi.modurang.domain.club.enums.Club;
import modi.modurang.domain.club.error.ClubError;
import modi.modurang.domain.homework.dto.request.HomeworkRequest;
import modi.modurang.domain.homework.entity.Homework;
import modi.modurang.domain.homework.repository.HomeworkRepository;
import modi.modurang.domain.user.entity.User;
import modi.modurang.domain.user.error.UserError;
import modi.modurang.domain.user.repository.UserRepository;
import modi.modurang.global.error.CustomException;
import modi.modurang.global.security.util.SecurityUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeworkServiceImpl implements HomeworkService {

    private final HomeworkRepository homeworkRepository;
    private final UserRepository userRepository;
    private final SecurityUtil securityUtil;

    @Transactional
    @Override
    public void createHomework(HomeworkRequest request) {
        User adminUser = securityUtil.currentUser();
        Club club = adminUser.getClub();

        List<Long> userIds = request.getUserId();

        for (Long id : userIds) {
            User user = userRepository.findById(id).orElseThrow(() -> new CustomException(UserError.USER_NOT_FOUND));

            if (!user.getClub().equals(club)) {
                throw new CustomException(ClubError.UNAUTHORIZED_CLUB_MEMBER);
            }

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