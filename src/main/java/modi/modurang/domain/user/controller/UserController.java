package modi.modurang.domain.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import modi.modurang.domain.user.dto.response.UserResponse;
import modi.modurang.domain.user.entity.User;
import modi.modurang.global.common.BaseResponse;
import modi.modurang.global.security.annotation.CurrentUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "user", description = "사용자 API")
@RestController
@RequestMapping("/user")
public class UserController {
    @Operation(description = "현재 사용자 정보 조회")
    @GetMapping("/me")
    public ResponseEntity<BaseResponse<UserResponse>> getCurrentUser(@CurrentUser User user) {
        UserResponse userResponse = UserResponse.from(user);
        return BaseResponse.of(userResponse, "사용자 정보 조회 성공");
    }
}