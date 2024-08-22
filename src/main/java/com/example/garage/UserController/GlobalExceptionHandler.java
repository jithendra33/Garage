package com.example.garage.UserController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.example.garage.UserEntity.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

  
    @ExceptionHandler(UserIsNotFoundException.class)
    public ResponseEntity<ResponseBody<?>> handleUserIsNotFoundException(UserIsNotFoundException ex, WebRequest request) {
        ResponseBody<String> body = new ResponseBody<>();
        body.setStatusCode(HttpStatus.CONFLICT.value());
        body.setStatus("FAILURE");
        body.setData(ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    // You can add more exception handlers as needed
}
