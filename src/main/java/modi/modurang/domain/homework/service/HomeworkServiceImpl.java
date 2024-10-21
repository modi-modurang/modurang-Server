package modi.modurang.domain.homework.service;

import lombok.RequiredArgsConstructor;
import modi.modurang.domain.club.enums.Club;
import modi.modurang.domain.homework.dto.request.HomeworkRequest;
import modi.modurang.domain.homework.entity.Homework;
import modi.modurang.domain.homework.repository.HomeworkRepository;
import modi.modurang.domain.user.entity.User;
import modi.modurang.domain.user.repository.UserRepository;
import modi.modurang.global.exception.CustomException;
import modi.modurang.global.exception.ErrorCode;
import modi.modurang.global.security.details.CustomUserDetails;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeworkServiceImpl implements HomeworkService {

    private final HomeworkRepository homeworkRepository;
    private final UserRepository userRepository;

    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public void createHomework(HomeworkRequest request, CustomUserDetails userDetails) {
        User adminUser = userDetails.user();
        Club club = adminUser.getClub();

        List<Long> userIds = request.getUserId();

        for (Long id : userIds) {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

            if (!user.getClub().equals(club)) {
                throw new CustomException(ErrorCode.UNAUTHORIZED_CLUB_MEMBER);
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
