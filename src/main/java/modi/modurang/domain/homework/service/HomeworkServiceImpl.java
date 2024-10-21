package modi.modurang.domain.homework.service;

import lombok.RequiredArgsConstructor;
import modi.modurang.domain.club.enums.Club;
import modi.modurang.domain.homework.dto.request.HomeworkRequest;
import modi.modurang.domain.homework.entity.Homework;
import modi.modurang.domain.homework.repository.HomeworkRepository;
import modi.modurang.domain.user.entity.User;
import modi.modurang.global.exception.CustomException;
import modi.modurang.global.exception.ErrorCode;
import modi.modurang.global.security.details.CustomUserDetails;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User adminUser = userDetails.user();
        Club club = adminUser.getClub();

        for (User user : request.getUserId()) {
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
