package com.sparta.board.controller;

import com.sparta.board.dto.AuthRequestDto;
import com.sparta.board.dto.BasicResponseDto;
import com.sparta.board.service.UserService;
import com.sparta.board.util.CustomStatusMessage;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {


    private final UserService userService;

    //username 과 password 특정 패턴인지 확인
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody AuthRequestDto requestDto) {
        CustomStatusMessage customStatusMessage = userService.signup(requestDto);
        return new ResponseEntity<>(BasicResponseDto.setSuccess(customStatusMessage), customStatusMessage.getStatus());
    }

    @PostMapping("/login-page")
    public  ResponseEntity<?> login(@RequestBody AuthRequestDto requestDto, HttpServletResponse response) {
        CustomStatusMessage customStatusMessage = userService.login(requestDto, response);
        return new ResponseEntity<>(BasicResponseDto.setSuccess(customStatusMessage), customStatusMessage.getStatus());
    }
}
