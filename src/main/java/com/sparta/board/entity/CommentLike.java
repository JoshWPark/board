package com.sparta.board.entity;

import com.sparta.board.security.UserDetailsImpl;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class CommentLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMMENT_ID")
    private Comment comment;

    private CommentLike(Comment comment, User user) {
        this.comment = comment;
        this.user = user;
    }

    public static CommentLike updateLike(Comment comment, UserDetailsImpl userDetails){
        return new CommentLike(comment, userDetails.getUser());
    }
}
