package com.sparta.board.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.board.dto.BasicResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        log.info("Error here");
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json; charset=utf8");

        String json = new ObjectMapper().writeValueAsString(BasicResponseDto.setBadRequest(accessDeniedException.getMessage(), HttpStatus.FORBIDDEN));
        response.getWriter().write(json);

    }
}
