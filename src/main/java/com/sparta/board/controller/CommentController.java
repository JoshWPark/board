package com.sparta.board.controller;

import com.sparta.board.dto.BasicResponseDto;
import com.sparta.board.dto.board.BoardRequestDto;
import com.sparta.board.dto.comment.CommentRequestDto;
import com.sparta.board.dto.comment.CommentResponseDto;
import com.sparta.board.entity.StatusErrorMessageEnum;
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
    public BasicResponseDto<CommentResponseDto> createComment(@RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        CommentResponseDto commentResponseDto = commentService.createComment(requestDto, userDetails.getUser());
        return BasicResponseDto.setSuccess(StatusErrorMessageEnum.CREATE_COMMENT.getMessage(),commentResponseDto);
    }

    //댓글 수정
    @PutMapping("/comment/{id}")
    public BasicResponseDto<CommentResponseDto> updateComment (@PathVariable Long id, @RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommentResponseDto commentResponseDto = commentService.updateComment(id, requestDto, userDetails);
        return BasicResponseDto.setSuccess(StatusErrorMessageEnum.UPDATE_COMMENT.getMessage(), commentResponseDto);
    }
    //댓글 삭제
    @DeleteMapping("/comment/{id}")
    public BasicResponseDto<?> deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        String message = commentService.deleteComment(id, userDetails);
        return BasicResponseDto.setSuccess(message);
    }
    //댓글 좋아요
    @PostMapping("/comment/like/{id}")
    public BasicResponseDto<?> updateLikeComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        String message = commentService.updateLikeComment(id, userDetails);
        return BasicResponseDto.setSuccess(message);
    }
}
