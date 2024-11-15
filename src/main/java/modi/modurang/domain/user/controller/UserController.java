package modi.modurang.domain.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import modi.modurang.domain.user.dto.response.UserResponse;
import modi.modurang.domain.user.service.UserService;
import modi.modurang.global.common.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "user", description = "유저 API")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "내 정보 조회")
    @GetMapping("/me")
    public ResponseEntity<BaseResponse<UserResponse>> getMe() {
        return BaseResponse.of(userService.getMe());
    }
}