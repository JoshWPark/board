package com.sparta.board.controller;

import com.sparta.board.dto.BasicResponseDto;
import com.sparta.board.dto.board.BoardRequestDto;
import com.sparta.board.dto.comment.CommentRequestDto;
import com.sparta.board.security.UserDetailsImpl;
import com.sparta.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class CommentController {

    private final CommentService commentService;
    //댓글 생성
    @PostMapping("/comment")
    public BasicResponseDto createComment(@RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.createComment(requestDto, userDetails.getUser());
    }
    //댓글 수정
    @PutMapping("/comment/{id}")
    public BasicResponseDto updateComment (@PathVariable Long id, @RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.updateComment(id, requestDto, userDetails);
    }
    //댓글 삭제
    @DeleteMapping("/comment/{id}")
    public BasicResponseDto deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.deleteComment(id, userDetails);
    }
}
