package modi.modurang.domain.file.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import modi.modurang.domain.file.service.FileService;
import modi.modurang.global.common.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "file", description = "파일 API")
@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @SneakyThrows
    @PostMapping("/upload")
    public ResponseEntity<BaseResponse<String>> uploadImage(@RequestParam("file") MultipartFile file) {
            return BaseResponse.of(fileService.saveImage(file), 200);
    }
}
