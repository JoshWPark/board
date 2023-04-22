package com.sparta.board.controller;

import com.sparta.board.dto.StatusResponseDto;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public StatusResponseDto signupError(MethodArgumentNotValidException e){
        return new StatusResponseDto(Objects.requireNonNull(e.getFieldError()).getDefaultMessage(),400);
    }

    @ExceptionHandler(RuntimeException.class)
    public StatusResponseDto runtimeError(RuntimeException e){
        return new StatusResponseDto(e.getMessage(),400);
    }

    @ExceptionHandler(Exception.class)
    public StatusResponseDto methodError(Exception e){
        return new StatusResponseDto(e.getMessage(),405);
    }

}
