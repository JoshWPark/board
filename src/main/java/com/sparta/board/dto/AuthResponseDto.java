package com.sparta.board.dto;

import com.sparta.board.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthResponseDto {
    private String msg;
    private int statusCode;

    public AuthResponseDto(String msg, int statusCode) {
        this.msg = msg;
        this.statusCode = statusCode;
    }
}
