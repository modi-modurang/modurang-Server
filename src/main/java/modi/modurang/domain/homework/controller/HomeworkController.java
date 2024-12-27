package modi.modurang.domain.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import modi.modurang.domain.homework.dto.request.HomeworkRequest;
import modi.modurang.domain.homework.service.HomeworkService;
import modi.modurang.domain.user.entity.User;
import modi.modurang.global.common.BaseResponse;
import modi.modurang.global.security.annotation.CurrentUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Homework", description = "숙제 API")
@RestController
@RequestMapping("/homework")
@RequiredArgsConstructor
public class HomeworkController {
    private final HomeworkService homeworkService;

    @Operation(summary = "숙제 등록")
    @PostMapping("/register")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<BaseResponse<Void>> createHomework(@CurrentUser User user, @Valid @RequestBody HomeworkRequest request) {
        homeworkService.createHomework(user, request);
        return BaseResponse.of(null, "숙제 등록 성공");
    }
}