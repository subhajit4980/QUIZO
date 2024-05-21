package com.subhajit.IdentityService.Exception;


import com.subhajit.IdentityService.Dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResponse> handleUserException(UserException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getErrorCode(),ex.getStatus(),ex.getStatusCode());
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }
}
