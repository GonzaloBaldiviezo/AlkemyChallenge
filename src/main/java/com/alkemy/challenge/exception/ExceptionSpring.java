package com.alkemy.challenge.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ExceptionSpring extends Exception {

    @Getter
    private HttpStatus status;

    public ExceptionSpring() {
    }

    public ExceptionSpring(String msg) {
        super(msg);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public ExceptionSpring(String msg, HttpStatus status) {
        super(msg);
        this.status = status;
    }
}

