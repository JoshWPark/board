package com.sparta.board.entity;

import com.sparta.board.dto.board.BoardRequestDto;
import com.sparta.board.dto.comment.CommentRequestDto;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID", nullable = false)
    private Board board;


    private Comment(String content, User author, Board blog) {
        this.content = content;
        this.user = author;
        this.board = blog;
    }

    public static Comment saveComment(CommentRequestDto requestDto, User user, Board board){
        return new Comment(requestDto.getContent(),user, board);
    }
    private void update(String content){
        this.content = content;
    }

    public void updateComment(BoardRequestDto requestDto) {
        update(requestDto.getContent());
    }
}
