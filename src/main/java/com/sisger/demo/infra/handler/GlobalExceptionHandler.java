package com.sisger.demo.infra.handler;

import com.sisger.demo.exception.*;
import com.sisger.demo.exception.details.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<InternalServerErroDetails> handleInternalServerErrorException(InternalServerErrorException ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                InternalServerErroDetails.builder()
                        .title("Internal Server Error")
                        .details(ex.getMessage())
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .developerMessage(ex.getClass().getName())
                        .timestamp(LocalDateTime.now())
                        .build()

        );
    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestDetails> handleBadRequestException(BadRequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                BadRequestDetails.builder()
                        .title("Bad Request check the documentation")
                        .details(ex.getMessage())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .developerMessage(ex.getClass().getName())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<UnauthorizedDetails> handleUnauthorizedException(UnauthorizedException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                UnauthorizedDetails.builder()
                        .title("Unauthorized request check the documentation")
                        .details(ex.getMessage())
                        .status(HttpStatus.UNAUTHORIZED.value())
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

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<EmailAlreadyExistsDetails> handleIllegalArgumentException(IllegalArgumentException ex) {
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