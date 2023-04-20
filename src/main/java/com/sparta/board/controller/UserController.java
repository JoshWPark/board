package com.sparta.board.controller;

import com.sparta.board.dto.AuthRequestDto;
import com.sparta.board.dto.AuthResponseDto;
import com.sparta.board.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board/auth")
public class UserController {

    private final UserService userService;

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public AuthResponseDto signupError(MethodArgumentNotValidException e){
        return new AuthResponseDto(Objects.requireNonNull(e.getFieldError()).getDefaultMessage(),400);
    }

    //username 과 password 특정 패턴인지 확인
    @PostMapping("/signup")
    public AuthResponseDto signup(@Valid @RequestBody  AuthRequestDto authRequestDto) {
        return userService.signup(authRequestDto);
    }

    @PostMapping("/login")
    public AuthResponseDto login(@RequestBody AuthRequestDto authRequestDto, HttpServletResponse response) {
        return userService.login(authRequestDto, response);
    }
}
