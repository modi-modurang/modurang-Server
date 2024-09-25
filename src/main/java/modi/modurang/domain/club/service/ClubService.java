package modi.modurang.domain.club.service;

import lombok.RequiredArgsConstructor;
import modi.modurang.domain.club.dto.request.ClubRequest;
import modi.modurang.domain.user.entity.User;
import modi.modurang.domain.user.enums.UserRole;
import modi.modurang.domain.user.repository.UserRepository;
import modi.modurang.global.exception.CustomException;
import modi.modurang.global.exception.ErrorCode;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClubService {

    private final UserRepository userRepository;

    public void join(ClubRequest request) {

        User user = userRepository.findByUsernameAndStudentNumber(request.getUsername(), request.getStudentNumber())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        user.setClub(request.getClub());

        user.setRole(UserRole.USER);
    }

    public void modify(ClubRequest request) {

        User user = userRepository.findByUsernameAndStudentNumber(request.getUsername(), request.getStudentNumber())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        user.setClub(request.getClub());
    }

    public void admin(ClubRequest request) {

        User user = userRepository.findByUsernameAndStudentNumber(request.getUsername(), request.getStudentNumber())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        user.setRole(UserRole.ADMIN);
    }
}
