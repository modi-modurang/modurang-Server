package modi.modurang.domain.club.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import modi.modurang.domain.club.dto.request.AdminRequest;
import modi.modurang.domain.club.dto.request.ClubRequest;
import modi.modurang.domain.club.enums.Club;
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

    @Transactional
    public void join(ClubRequest request) {

        User user = userRepository.findByUsernameAndStudentNumber(request.getUsername(), request.getStudentNumber())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (Club.isValidClub(request.getClub())) {
            throw new CustomException(ErrorCode.INVALID_CLUB);
        }

        user.setClub(request.getClub());
        user.setRole(UserRole.USER);

        userRepository.save(user);
    }

    @Transactional
    public void modify(ClubRequest request) {

        User user = userRepository.findByUsernameAndStudentNumber(request.getUsername(), request.getStudentNumber())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (Club.isValidClub(request.getClub())) {
            throw new CustomException(ErrorCode.INVALID_CLUB);
        }

        if (user.getClub().equals(request.getClub())) {
            throw new CustomException(ErrorCode.ALREADY_JOINED_CLUB);
        }

        user.setClub(request.getClub());
        user.setRole(UserRole.USER);

        userRepository.save(user);
    }

    @Transactional
    public void admin(AdminRequest request) {

        User user = userRepository.findByUsernameAndStudentNumber(request.getUsername(), request.getStudentNumber())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        user.setRole(UserRole.ADMIN);

        userRepository.save(user);
    }
}
