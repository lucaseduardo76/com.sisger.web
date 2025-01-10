package com.sisger.demo.exception;

public class CpfAlreadyExistsException extends RuntimeException{
    public CpfAlreadyExistsException(String message) {
        super(message);
    }
}
