package com.example.ogame.exeptions;

public class ApiRequestException extends RuntimeException {

    public ApiRequestException(String message) {
        super(message);
    }
}
