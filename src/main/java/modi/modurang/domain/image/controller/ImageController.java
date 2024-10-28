package modi.modurang.domain.image.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import modi.modurang.domain.image.service.ImageService;
import modi.modurang.global.common.BaseResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Slf4j
@Tag(name = "이미지", description = "Image")
@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {

    @Value("${spring.servlet.multipart.location:uploads}")
    private String uploadDir;

    private final ImageService imageService;

    @SneakyThrows
    @PostMapping("/upload")
    public ResponseEntity<BaseResponse<String>> uploadImage(@RequestParam("file") MultipartFile file) {
            return BaseResponse.of(imageService.saveImage(file), 200);
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String fileName) throws IOException {
        File file = new File(uploadDir, fileName);

        if (!file.exists()) {
                return ResponseEntity.notFound().build();
            }

        byte[] imageBytes = Files.readAllBytes(file.toPath());

        String mimeType = Files.probeContentType(file.toPath());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, mimeType)
                .body(imageBytes);
    }
}
