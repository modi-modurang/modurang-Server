package modi.modurang.global.error;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
@Builder
public class ErrorResponseEntity {

    private int status;
    private String name;
    private String message;

    public static ResponseEntity<ErrorResponseEntity> of(ErrorCode e) {
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(ErrorResponseEntity.builder()
                        .status(e.getHttpStatus().value())
                        .name(e.name())
                        .message(e.getMessage())
                        .build());
    }
}
