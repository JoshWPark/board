package com.sparta.board.controller;

import com.sparta.board.dto.AuthRequestDto;
import com.sparta.board.dto.AuthResponseDto;
import com.sparta.board.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board/auth")
public class UserController {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public AuthResponseDto signupError(MethodArgumentNotValidException e){
        return new AuthResponseDto(e.getFieldError().getDefaultMessage(), 400);
    }

    private final UserService userService;

    @PostMapping("/signup")
    public AuthResponseDto signup(@RequestBody @Validated AuthRequestDto authRequestDto) {
            return userService.signup(authRequestDto);
    }

    @PostMapping("/login")
    public AuthResponseDto login(@RequestBody AuthRequestDto authRequestDto, HttpServletResponse response) {
        return userService.login(authRequestDto, response);
    }
}
