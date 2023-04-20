package com.sparta.board.controller;

import com.sparta.board.dto.AuthRequestDto;
import com.sparta.board.dto.AuthResponseDto;
import com.sparta.board.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

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
    public AuthResponseDto signup(@Valid @RequestBody AuthRequestDto requestDto) {
        return userService.signup(requestDto);
    }

    @PostMapping("/login")
    public AuthResponseDto login(@RequestBody AuthRequestDto requestDto, HttpServletResponse response) {
        return userService.login(requestDto, response);
    }
}
