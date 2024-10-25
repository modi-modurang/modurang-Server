package modi.modurang.domain.image.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;

    private String fileName;        // 원본 파일 이름 (사용자가 업로드한 파일명)
    private Long fileSize;          // 파일 크기 (바이트 단위)
    private String filePath;        // 이미지 경로 (로컬/클라우드 저장소 경로)
    private String contentType;     // MIME 타입 (image/jpeg, image/png 등)

}
