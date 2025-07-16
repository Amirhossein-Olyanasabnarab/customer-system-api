package dk.dev.app.controller;

import dk.dev.app.dto.ErrorResponse;
import dk.dev.app.exception.CustomerNotFoundException;
import dk.dev.app.exception.DuplicatedCustomerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCustomerNotFoundException(CustomerNotFoundException exception) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicatedCustomerException.class)
    public ResponseEntity<ErrorResponse> handleDuplicatedCustomerException(DuplicatedCustomerException exception) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT.value(),
                exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
}
