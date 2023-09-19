package com.manish.user.exception;

import com.manish.user.dto.ErrorResponseDTO;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class ResponseException {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataValidation(ConstraintViolationException e) {
        log.info("|| thrown an ConstraintViolationException with error message : {} ||", e.getMessage());

        Map<String, String> errors = new HashMap<>();
        e.getConstraintViolations().forEach(violation -> errors
                .put(String.valueOf(violation.getPropertyPath()), violation.getMessage()));

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponseDTO> handleApplicationException(ApplicationException e) {
        log.info("|| thrown an ApplicationException with error message : {} ||", e.getMessage());

        return new ResponseEntity<>(new ErrorResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
