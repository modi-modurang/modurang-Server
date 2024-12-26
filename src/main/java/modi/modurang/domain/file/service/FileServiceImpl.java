package modi.modurang.domain.file.service;

import lombok.RequiredArgsConstructor;
import modi.modurang.domain.file.entity.File;
import modi.modurang.domain.file.repository.FileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    @Value("${spring.servlet.multipart.location}")
    private String uploadDir;

    @Transactional
    @Override
    public String saveImage(MultipartFile file) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        java.io.File destinationFile = new java.io.File(uploadDir, fileName);

        file.transferTo(destinationFile);

        File image = File.builder()
                .fileName(fileName)
                .url("/file/view/" + fileName)
                .size(file.getSize())
                .mimeType(file.getContentType())
                .build();

        fileRepository.save(image);

        return image.getUrl();
    }
}