package com.sisger.demo.infra.handler;

import com.sisger.demo.exception.CpfAlreadyExistsException;
import com.sisger.demo.exception.EmailAlreadyExistsException;
import com.sisger.demo.exception.UnauthorizedException;
import com.sisger.demo.exception.details.CpfAlreadyExistsDetails;
import com.sisger.demo.exception.details.EmailAlreadyExistsDetails;
import com.sisger.demo.exception.details.UnauthorizedExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<UnauthorizedExceptionDetails> handleUnauthorizedException(UnauthorizedException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                UnauthorizedExceptionDetails.builder()
                        .title("Bad Request check the documentation")
                        .details(ex.getMessage())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .developerMessage(ex.getClass().getName())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @ExceptionHandler(CpfAlreadyExistsException.class)
    public ResponseEntity<CpfAlreadyExistsDetails> handleCpfAlreadyExistsException(CpfAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                CpfAlreadyExistsDetails.builder()
                        .title("Bad Request check the documentation")
                        .details(ex.getMessage())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .developerMessage(ex.getClass().getName())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<EmailAlreadyExistsDetails> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                EmailAlreadyExistsDetails.builder()
                        .title("Bad Request check the documentation")
                        .details(ex.getMessage())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .developerMessage(ex.getClass().getName())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

}