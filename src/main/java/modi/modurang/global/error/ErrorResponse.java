package modi.modurang.global.error;

import org.springframework.http.ResponseEntity;

public record ErrorResponse(int status, String message, String code) {
    public static ResponseEntity<ErrorResponse> of(CustomError error) {
        return ResponseEntity.status(error.getStatus()).body(new ErrorResponse(error.getStatus(), error.getMessage(), error.getCode()));
    }
}
