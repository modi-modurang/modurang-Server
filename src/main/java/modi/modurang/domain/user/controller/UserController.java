package modi.modurang.domain.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import modi.modurang.domain.user.dto.response.UserResponse;
import modi.modurang.domain.user.entity.User;
import modi.modurang.global.security.annotation.CurrentUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "user", description = "유저 API")
@RestController
@RequestMapping("/user")
public class UserController {
    @Operation(description = "내 정보 조회")
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