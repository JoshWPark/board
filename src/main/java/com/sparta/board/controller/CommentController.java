package com.sparta.board.controller;

import com.sparta.board.dto.BasicResponseDto;
import com.sparta.board.dto.board.BoardRequestDto;
import com.sparta.board.dto.comment.CommentRequestDto;
import com.sparta.board.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class CommentController {

    private final CommentService commentService;
    //댓글 생성
    @PostMapping("/comment")
    public BasicResponseDto createComment(@RequestBody CommentRequestDto requestDto, HttpServletRequest request){
        return commentService.createComment(requestDto, request);
    }
    //댓글 수정
    @PutMapping("/comment/{id}")
    public BasicResponseDto updateComment (@PathVariable Long id, @RequestBody BoardRequestDto requestDto, HttpServletRequest request) {
        return commentService.updateComment(id, requestDto, request);
    }
    //댓글 삭제
    @DeleteMapping("/comment/{id}")
    public BasicResponseDto deleteComment(@PathVariable Long id, HttpServletRequest request){
        return commentService.deleteComment(id, request);
    }
}
