package modi.modurang.domain.image.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import modi.modurang.domain.image.service.FileService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.util.Collections;
import java.util.Optional;


@Tag(name = "이미지", description = "Image")
@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("파일이 잘못되었습니다.");
        }
        boolean isSaved = fileService.saveImage(Collections.singletonList(file));
        if (isSaved) {
            return ResponseEntity.ok("파일이 성공적으로 업로드되었습니다.");
        }
        else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 업로드에 실패했습니다.");
        }
    }

    @GetMapping("/view")
    public ResponseEntity<String> getFile() {
        Optional<String> filePath = fileService.getAllImages();
        if (!filePath.isPresent()) {
            String[] fileNames = filePath.get().split("////");
            String fileName = fileNames[fileNames.length - 1];
            return ResponseEntity.ok(fileName);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("파일을 찾을 수 없습니다.");
        }
    }

    @GetMapping("/{filename}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) throws MalformedURLException {
        Resource fileResource = new UrlResource("file:"
                + "/Users/rlaehdgus08/Documents/GitHub/modurang/src/main/resources/static/savedfile"
                + filename);

        if (fileResource.exists()) {
            return ResponseEntity.ok(fileResource);
        }
        else  {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


}
