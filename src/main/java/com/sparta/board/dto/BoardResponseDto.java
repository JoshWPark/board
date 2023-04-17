package com.sparta.board.dto;

import com.sparta.board.entity.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardResponseDto {
    private String author;
    private String title;
    private String message;

    public BoardResponseDto(Board board) {
        this.author = board.getAuthor();
        this.title = board.getTitle();
        this.message = board.getContent();
    }
}
