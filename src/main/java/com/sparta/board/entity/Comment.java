package com.sparta.board.entity;

import com.sparta.board.dto.BoardRequestDto;
import com.sparta.board.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private String username;


    private Comment(String content, String user) {
        this.content = content;
        this.username = user;
    }

    public static Comment saveComment(CommentRequestDto requestDto, User user){
        return new Comment(requestDto.getContent(),user.getUsername());
    }
    private void update(String content){
        this.content = content;
    }

    public void updateComment(BoardRequestDto requestDto) {
        update(requestDto.getContent());
    }
}
