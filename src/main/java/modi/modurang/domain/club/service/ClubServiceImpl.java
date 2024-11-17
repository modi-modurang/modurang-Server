package modi.modurang.domain.club.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import modi.modurang.domain.club.dto.request.AdminRequest;
import modi.modurang.domain.club.dto.request.ClubRequest;
import modi.modurang.domain.club.dto.request.MemberRequest;
import modi.modurang.domain.club.error.ClubError;
import modi.modurang.domain.user.dto.response.UserResponse;
import modi.modurang.domain.user.entity.User;
import modi.modurang.domain.user.enums.UserRole;
import modi.modurang.domain.user.error.UserError;
import modi.modurang.domain.user.repository.UserRepository;
import modi.modurang.global.error.CustomException;
import modi.modurang.global.util.SecurityUtil;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClubServiceImpl implements ClubService {

    private final UserRepository userRepository;
    private final SecurityUtil securityUtil;

    @Transactional
    @Override
    public List<UserResponse> clubMemberList(MemberRequest request) {
        List<User> users = Collections.singletonList(userRepository.findAllByClub(request.getClub())
                .orElseThrow(() -> new CustomException(ClubError.NO_MEMBERS_FOUND)));

        return users.stream()
                .map(user -> new UserResponse(user.getId(), user.getUsername(), user.getStudentNumber(),
                        user.getEmail(), user.getClub()))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void joinClub(ClubRequest request) {
        User user = securityUtil.currentUser();

        user.setClub(request.getClub());
        user.setRole(UserRole.USER);

        userRepository.save(user);
    }

    @Transactional
    @Override
    public void modifyClub(ClubRequest request) {
        User user = securityUtil.currentUser();

        if (user.getClub().equals(request.getClub())) {
            throw new CustomException(ClubError.ALREADY_JOINED_CLUB);
        }

        user.setClub(request.getClub());
        user.setRole(UserRole.USER);

        userRepository.save(user);
    }

    @Transactional
    @Override
    public void admin(AdminRequest request) {
        User user = userRepository.findByUsernameAndStudentNumber(request.getUsername(), request.getStudentNumber())
                .orElseThrow(() -> new CustomException(UserError.USER_NOT_FOUND));

        user.setRole(UserRole.ADMIN);

        userRepository.save(user);
    }
}
