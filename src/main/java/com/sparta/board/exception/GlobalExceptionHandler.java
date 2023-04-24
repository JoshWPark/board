package com.sparta.board.exception;

import com.sparta.board.dto.BasicResponseDto;
import com.sparta.board.entity.StatusCode;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BasicResponseDto signupError(MethodArgumentNotValidException e){
        return BasicResponseDto.setBadRequest(Objects.requireNonNull(e.getFieldError()).getDefaultMessage(), StatusCode.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public BasicResponseDto runtimeError(RuntimeException e){
        return BasicResponseDto.setBadRequest(e.getMessage(),StatusCode.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public BasicResponseDto methodError(Exception e){
        return BasicResponseDto.setBadRequest(e.getMessage(),StatusCode.INTERNAL_SERVER_ERROR);
    }

}
