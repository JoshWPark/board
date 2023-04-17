package com.sparta.board.entity;

import com.sparta.board.dto.BoardRequestDto;
import com.sparta.board.dto.BoardResponseDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String author;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String message;
    @Column(nullable = false)
    private String password;

    public Board(BoardRequestDto requestDto) {
        this.author = requestDto.getAuthor();
        this.title = requestDto.getTitle();
        this.message = requestDto.getMessage();
        this.password = requestDto.getPassword();
    }

    public void update(BoardRequestDto requestDto) {
        this.author = requestDto.getAuthor();
        this.title = requestDto.getTitle();
        this.message = requestDto.getMessage();
        this.password = requestDto.getPassword();
    }
}
