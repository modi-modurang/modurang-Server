package modi.modurang.domain.file.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    String saveImage(MultipartFile file) throws IOException;
}