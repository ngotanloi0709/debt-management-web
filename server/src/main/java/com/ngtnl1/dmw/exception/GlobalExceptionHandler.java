package com.ngtnl1.dmw.exception;

import java.time.Instant;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ngtnl1.dmw.dto.authentication.ErrorResponseDTO;
import com.ngtnl1.dmw.dto.authentication.ErrorResponseDTO.ErrorItem;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidBody(
            MethodArgumentNotValidException exception, HttpServletRequest request) {

        List<ErrorItem> errorMessages = exception.getBindingResult().getAllErrors().stream()
                .map(err -> new ErrorItem(
                        err.getDefaultMessage()))
                .toList();

        ErrorResponseDTO body = new ErrorResponseDTO(
                Instant.now(), // timestamp
                HttpStatus.BAD_REQUEST.value(), // status
                HttpStatus.BAD_REQUEST.getReasonPhrase(), // error
                request.getRequestURI(), // path
                errorMessages); // all error messages

        return ResponseEntity.badRequest().body(body);
    }
}
