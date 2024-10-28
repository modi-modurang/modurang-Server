package modi.modurang.domain.image.service;

import lombok.RequiredArgsConstructor;
import modi.modurang.domain.image.entity.Image;
import modi.modurang.domain.image.repository.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    public String saveImage(MultipartFile file) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        File destinationFile = new File("upload" + File.separator + fileName); // 예: "uploads/1680001234567_example.jpg"

        file.transferTo(destinationFile);

        Image image = Image.builder()
                .fileName(fileName)
                .url("/upload/" + fileName)
                .size(file.getSize())
                .mimeType(file.getContentType())
                .build();

        imageRepository.save(image);

        return image.getUrl();
    }
}
