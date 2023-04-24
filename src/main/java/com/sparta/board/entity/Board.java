package com.sparta.board.entity;

import com.sparta.board.dto.board.BoardRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;
    @OneToMany(mappedBy = "board", cascade =  CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<>();

    private Board(String title, String content, User author) {
        this.title = title;
        this.content = content;
        this.user = author;
    }

    public static Board saveBoard(BoardRequestDto requestDto, User user){
        return new Board(requestDto.getTitle(),requestDto.getContent(), user);
    }

    private void update(String title, String content){
        this.title = title;
        this.content = content;
    }

    public void updateBoard(BoardRequestDto requestDto) {
        update(requestDto.getTitle(), requestDto.getContent());
    }

    public void addComment(Comment comment){
        this.commentList.add(comment);
    }
}
