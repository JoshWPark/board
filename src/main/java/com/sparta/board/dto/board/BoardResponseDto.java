package com.sparta.board.dto.board;

import com.sparta.board.dto.comment.CommentResponseDto;
import com.sparta.board.entity.Board;
import com.sparta.board.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Getter
@NoArgsConstructor
public class BoardResponseDto {
    private Long id;
    private String username;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<CommentResponseDto> commentList;
    private int likes;

    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.username = board.getUser().getUsername();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
        this.likes = board.getLikes();
        this.commentList = board.getCommentList()
                .stream().sorted(Comparator.comparing(Comment::getCreatedAt).reversed())
                .map(CommentResponseDto::new).toList();

    }
}
