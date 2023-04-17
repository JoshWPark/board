package com.sparta.board.dto;

import com.sparta.board.entity.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardResponseDto {
    private Long id;
    private String author;
    private String title;
    private String message;
    private String password;

    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.author = board.getAuthor();
        this.title = board.getTitle();
        this.message = board.getMessage();
        this.password = board.getPassword();
    }
}
