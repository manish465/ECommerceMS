package com.manish.auth.exception;

import com.manish.auth.dto.ErrorResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ResponseException {
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponseDTO> handleApplicationException(ApplicationException e){
        log.info("|| thrown an ApplicationException with error message : {} ||", e.getMessage());

        return new ResponseEntity<>(new ErrorResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
