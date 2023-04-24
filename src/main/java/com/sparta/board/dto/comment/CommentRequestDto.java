package com.sparta.board.dto.comment;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequestDto {
    private Long post_id;
    private String content;
}
