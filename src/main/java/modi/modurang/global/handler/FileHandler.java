package modi.modurang.global.handler;


import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


@Component
public class FileHandler {
    public String save(MultipartFile image) throws IOException {

        String FileId = UUID.randomUUID().toString();

        String fullPathName = "/Users/rlaehdgus08/Documents/GitHub/modurang/src/main/resources/static/savedfile"
                + FileId;
        image.transferTo(new File(fullPathName));
        return fullPathName;
    }
}
