package modi.modurang.domain.user.controller;

import modi.modurang.domain.user.dto.response.UserResponse;
import modi.modurang.domain.user.entity.User;
import modi.modurang.global.security.annotation.CurrentUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping("/me")
    public UserResponse getMe(@CurrentUser User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getStudentNumber(),
                user.getEmail(),
                user.getClub()
        );
    }
}