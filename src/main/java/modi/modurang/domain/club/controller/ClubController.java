package modi.modurang.domain.club.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import modi.modurang.domain.club.dto.request.AdminRequest;
import modi.modurang.domain.club.dto.request.ClubRequest;
import modi.modurang.domain.club.service.ClubService;
import modi.modurang.global.common.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/club")
@RequiredArgsConstructor
public class ClubController {

    private final ClubService clubService;

    @PostMapping("/join")
    public ResponseEntity<BaseResponse<Void>> join(@Valid @RequestBody ClubRequest request) {
        clubService.join(request);
        return BaseResponse.of(null);
    }

    @PutMapping("/modify")
    public ResponseEntity<BaseResponse<Void>> modify(@Valid @RequestBody ClubRequest request) {
        clubService.modify(request);
        return BaseResponse.of(null);
    }

    @PostMapping("/admin")
    public ResponseEntity<BaseResponse<Void>> admin(@Valid @RequestBody AdminRequest request) {
        clubService.admin(request);
        return BaseResponse.of(null);
    }
}
