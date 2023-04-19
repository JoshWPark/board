package com.sparta.board.controller;

import com.sparta.board.dto.LoginRequestDto;
import com.sparta.board.dto.SignupRequestDto;
import com.sparta.board.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board/auth")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequestDto signupRequestDto) {
        return userService.signup(signupRequestDto);
    }

//    @ResponseBody
//    @PostMapping("/login")
//    public String login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
//        userService.login(loginRequestDto, response);
//        return "success";
//    }
}
