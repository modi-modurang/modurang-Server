package modi.modurang.domain.club.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import modi.modurang.domain.club.dto.request.AdminRequest;
import modi.modurang.domain.club.dto.request.ClubRequest;
import modi.modurang.domain.club.dto.request.MemberRequest;
import modi.modurang.domain.club.service.ClubService;
import modi.modurang.domain.user.dto.response.UserResponse;
import modi.modurang.global.common.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "club", description = "동아리 API")
@RestController
@RequestMapping("/club")
@RequiredArgsConstructor
public class ClubController {

    private final ClubService clubService;

    @Operation(summary = "동아리 멤버 목록 조회")
    @GetMapping("")
    public ResponseEntity<BaseResponse<List<UserResponse>>> clubMemberList(@Valid @RequestBody MemberRequest request) {
        return BaseResponse.of(clubService.clubMemberList(request), 200);
    }

    @Operation(summary = "동아리 가입 신청")
    @PostMapping("/join")
    public ResponseEntity<BaseResponse<Void>> joinClub(@Valid @RequestBody ClubRequest request) {
        clubService.joinClub(request);
        return BaseResponse.of(null);
    }

    @Operation(summary = "동아리 정보 수정")
    @PatchMapping("/modify")
    public ResponseEntity<BaseResponse<Void>> modifyClub(@Valid @RequestBody ClubRequest request) {
        clubService.modifyClub(request);
        return BaseResponse.of(null, 204);
    }

    @Operation(summary = "동아리 관리자 권한 설정")
    @PostMapping("/admin")
    public ResponseEntity<BaseResponse<Void>> admin(@Valid @RequestBody AdminRequest request) {
        clubService.admin(request);
        return BaseResponse.of(null, 204);
    }
}