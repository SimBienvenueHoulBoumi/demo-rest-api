package simdev.demo.exceptions;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for managing application errors.
 */
@ControllerAdvice
public final class GlobalExceptionHandler {

    /**
     * Handles TaskNotFoundException and returns a 404 response with error
     * details.
     * @param ex the TaskNotFoundException to handle
     * @return a ResponseEntity with the error message and timestamp
     */
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleTaskNotFoundException(
            final TaskNotFoundException ex) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("error", ex.getMessage());
        errorBody.put("timestamp", Instant.now().toString());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorBody);
    }
}
