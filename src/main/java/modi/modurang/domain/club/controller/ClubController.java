package modi.modurang.domain.club.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import modi.modurang.domain.club.dto.request.AdminRequest;
import modi.modurang.domain.club.dto.request.ClubRequest;
import modi.modurang.domain.club.service.ClubService;
import modi.modurang.global.common.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "동아리", description = "Club")
@RestController
@RequestMapping("/club")
@RequiredArgsConstructor
public class ClubController {

    private final ClubService clubService;

    @Operation(summary = "동아리 가입")
    @PostMapping("/join")
    public ResponseEntity<BaseResponse<Void>> join(@Valid @RequestBody ClubRequest request) {
        clubService.join(request);
        return BaseResponse.of(null);
    }

    @Operation(summary = "동아리 수정")
    @PutMapping("/modify")
    public ResponseEntity<BaseResponse<Void>> modify(@Valid @RequestBody ClubRequest request) {
        clubService.modify(request);
        return BaseResponse.of(null);
    }

    @Operation(summary = "동아리 관리자")
    @PostMapping("/admin")
    public ResponseEntity<BaseResponse<Void>> admin(@Valid @RequestBody AdminRequest request) {
        clubService.admin(request);
        return BaseResponse.of(null);
    }
}
