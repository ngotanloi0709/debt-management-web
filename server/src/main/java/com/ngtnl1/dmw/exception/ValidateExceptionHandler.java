package com.ngtnl1.dmw.exception;

import com.ngtnl1.dmw.dto.authentication.ErrorResponseDTO;
import com.ngtnl1.dmw.dto.authentication.ErrorResponseDTO.ErrorItem;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidateExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ValidateExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationException(
            MethodArgumentNotValidException exception, HttpServletRequest request) {

        List<ErrorItem> errorMessages = exception.getBindingResult().getAllErrors().stream()
                .map(err -> new ErrorItem(err.getDefaultMessage(), err.getCode()))
                .toList();

        ErrorResponseDTO body = new ErrorResponseDTO(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Validation Failed",
                request.getRequestURI(),
                errorMessages);

        logger.warn("Validation error at {}: {}", request.getRequestURI(), errorMessages);
        return ResponseEntity.badRequest().body(body);
    }
}
