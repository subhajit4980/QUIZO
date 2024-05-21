package com.subhajit.IdentityService.Exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@Setter
public class UserException extends RuntimeException {
    private String errorCode="INVALID_REQUEST";
    private HttpStatus status;
    private HttpStatusCode statusCode;
    public UserException(String message, String errorCode,HttpStatus status) {
        super(message);
        this.errorCode = errorCode;
        this.status=status;
        this.statusCode=status;
    }
}
