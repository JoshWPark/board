package com.sparta.board.entity;

import com.sparta.board.security.UserDetailsImpl;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class BoardLike {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    private BoardLike(Board board, User user){
        this.user = user;
        this.board = board;
    }

    public static BoardLike updateLike(Board board, UserDetailsImpl userDetails){
        return new BoardLike(board, userDetails.getUser());
    }
}
