package com.sparta.board.controller;

import com.sparta.board.dto.AuthRequestDto;
import com.sparta.board.dto.AuthResponseDto;
import com.sparta.board.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board/auth")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public AuthResponseDto signup(@RequestBody AuthRequestDto authRequestDto) {
        return userService.signup(authRequestDto);
    }

    @PostMapping("/login")
    public AuthResponseDto login(@RequestBody AuthRequestDto authRequestDto, HttpServletResponse response) {
        return userService.login(authRequestDto, response);
    }
}
