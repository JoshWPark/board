package com.sparta.board.controller;

import com.sparta.board.dto.AuthRequestDto;
import com.sparta.board.dto.BasicResponseDto;
import com.sparta.board.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class UserController {


    private final UserService userService;

    //username 과 password 특정 패턴인지 확인
    @PostMapping("/signup")
    public BasicResponseDto<?> signup(@Valid @RequestBody AuthRequestDto requestDto) {
        String message = userService.signup(requestDto);
        return BasicResponseDto.setSuccess(message);
    }

    @PostMapping("/login-page")
    public  BasicResponseDto<?> login(@RequestBody AuthRequestDto requestDto, HttpServletResponse response) {
        String message = userService.login(requestDto, response);
        return BasicResponseDto.setSuccess(message);
    }
}
