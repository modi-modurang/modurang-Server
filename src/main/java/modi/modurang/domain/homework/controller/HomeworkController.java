package modi.modurang.domain.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import modi.modurang.domain.homework.dto.request.HomeworkRequest;
import modi.modurang.domain.homework.service.HomeworkService;
import modi.modurang.global.common.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "숙제", description = "Homework")
@RestController
@RequestMapping("/homework")
@RequiredArgsConstructor
public class HomeworkController {

    private final HomeworkService homeworkService;

    @Operation(summary = "숙제 등록")
    @PostMapping("/register")
    public ResponseEntity<BaseResponse<Void>> createHomework(@Valid @RequestBody HomeworkRequest request) {
        homeworkService.createHomework(request);
        return BaseResponse.of(null);
    }
}
