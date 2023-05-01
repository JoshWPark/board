package com.sparta.board.exception;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.board.dto.BasicResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json; charset=utf8");

        String json = new ObjectMapper().writeValueAsString(BasicResponseDto.setBadRequest(authException.getMessage(), HttpStatus.UNAUTHORIZED));
        response.getWriter().write(json);
    }

}
