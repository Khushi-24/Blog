package com.demo.blog.exceptions;

import com.demo.blog.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex){
        ApiResponse apiResponse = new ApiResponse(ex.getMessage());
        return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
    }
}
