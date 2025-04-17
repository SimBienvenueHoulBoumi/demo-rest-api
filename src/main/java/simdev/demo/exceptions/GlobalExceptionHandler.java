package simdev.demo.exceptions;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandler {
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleTaskNotFoundException(TaskNotFoundException ex) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("error", ex.getMessage());
        errorBody.put("timestamp", Instant.now().toString());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorBody);
    }

}
