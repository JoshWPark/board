package com.sparta.board.dto;

import lombok.Getter;

@Getter
public class BoardRequestDto {
    private String author;
    private String title;
    private String message;
    private String password;
}
