package modi.modurang.global.error;

import org.springframework.http.ResponseEntity;
import java.util.Map;

public record ErrorResponse(
        int status,
        String message,
        String code,
        Map<String, String> errors
) {
    public static ResponseEntity<ErrorResponse> of(CustomError error) {
        return ResponseEntity
                .status(error.getStatus())
                .body(new ErrorResponse(error.getStatus(), error.getMessage(), error.getCode(), null));
    }

    public static ResponseEntity<ErrorResponse> of(int status, String message, String code, Map<String, String> errors) {
        return ResponseEntity
                .status(status)
                .body(new ErrorResponse(status, message, code, errors));
    }
}