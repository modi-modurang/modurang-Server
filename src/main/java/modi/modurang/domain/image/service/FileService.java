package modi.modurang.domain.image.service;

import lombok.RequiredArgsConstructor;
import modi.modurang.domain.image.entity.File;
import modi.modurang.domain.image.repository.FileRepository;
import modi.modurang.global.handler.FileHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileHandler fileHandler;
    private final FileRepository fileRepository;

    public boolean saveImage(List<MultipartFile> images) {
        images.stream()
                .map(image -> {
                    try {
                        return fileHandler.save(image);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(path -> path != null)
                .map(path -> {
                    File fileEntity = new File();
                    fileEntity.setFilePath(path);
                    return fileEntity;
                })
                .forEach(fileRepository::save);
        return true;
    }
    public Optional<String> getAllImages() {
        List<File> files = fileRepository.findAll();
        if (files.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(files.get(0).getFilePath());
    }
}
