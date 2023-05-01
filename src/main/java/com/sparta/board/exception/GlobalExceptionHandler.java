package com.sparta.board.exception;

import com.sparta.board.dto.BasicResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomError.class)
    public ResponseEntity<?> customRuntimeError(CustomError e){
        return new ResponseEntity<>(BasicResponseDto.setBadRequest(e.getCustomStatusMessage().getMessage(), e.getCustomStatusMessage().getStatus()), e.getCustomStatusMessage().getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> signupError(MethodArgumentNotValidException e){
        return new ResponseEntity<>(BasicResponseDto.setBadRequest(Objects.requireNonNull(e.getFieldError()).getDefaultMessage(), e.getStatusCode().value()), e.getStatusCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> uncheckedError(Exception e){
        return new ResponseEntity<>(BasicResponseDto.setBadRequest(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(Exception.class)
//    public BasicResponseDto methodError(Exception e){
//        return BasicResponseDto.setBadRequest(e.getMessage(), );
//    }
}
