package com.sparta.board.entity;

import com.sparta.board.dto.BoardRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Board extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private String username;


    private Board(String title, String content, String username) {
        this.title = title;
        this.content = content;
        this.username = username;
    }

    public static Board saveBoard(BoardRequestDto requestDto, String username){
        return new Board(requestDto.getTitle(),requestDto.getContent(),username);
    }

    private void update(String title, String content){
        this.title = title;
        this.content = content;
    }

    public void updateBoard(BoardRequestDto requestDto) {
        update(requestDto.getTitle(), requestDto.getContent());
    }
}
