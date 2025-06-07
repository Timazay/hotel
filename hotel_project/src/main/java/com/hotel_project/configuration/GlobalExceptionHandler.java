package com.hotel_project.configuration;
import com.hotel_project.common.exceptions.ApplicationException;
import com.hotel_project.common.exceptions.BadRequestException;
import com.hotel_project.common.exceptions.ExceptionResponse;
import com.hotel_project.common.exceptions.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> handleBadRequestException(ApplicationException exception) {
        return ResponseEntity
                .status(400)
                .body(new ExceptionResponse(
                        exception.getMessage(),
                        exception.getMetadata()
                ));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundException(ApplicationException exception) {
        return ResponseEntity
                .status(404)
                .body(new ExceptionResponse(
                        exception.getMessage(),
                        exception.getMetadata()
                ));
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ExceptionResponse> handleServiceException(Exception exception) {
        return ResponseEntity
                .status(500)
                .body(new ExceptionResponse("Something wrong goes with database",
                        Map.of("message", exception.getMessage())));

    }
}
