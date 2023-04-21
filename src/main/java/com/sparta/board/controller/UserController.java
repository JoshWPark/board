package com.sparta.board.controller;

import com.sparta.board.dto.AuthRequestDto;
import com.sparta.board.dto.StatusResponseDto;
import com.sparta.board.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class UserController {

    private final UserService userService;

    //username 과 password 특정 패턴인지 확인
    @PostMapping("/signup")
    public StatusResponseDto signup(@Valid @RequestBody AuthRequestDto requestDto) {
        return userService.signup(requestDto);
    }

    @PostMapping("/login")
    public StatusResponseDto login(@RequestBody AuthRequestDto requestDto, HttpServletResponse response) {
        return userService.login(requestDto, response);
    }
}
