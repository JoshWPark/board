package com.sparta.board.controller;

import com.sparta.board.dto.BasicResponseDto;
import com.sparta.board.dto.board.BoardRequestDto;
import com.sparta.board.dto.comment.CommentRequestDto;
import com.sparta.board.dto.comment.CommentResponseDto;
import com.sparta.board.util.CustomStatusMessage;
import com.sparta.board.security.UserDetailsImpl;
import com.sparta.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;
    //댓글 생성
    @PostMapping("/comment")
    public ResponseEntity<?> createComment(@RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        CommentResponseDto commentResponseDto = commentService.createComment(requestDto, userDetails.getUser());
        return new ResponseEntity<>(BasicResponseDto.setSuccess(CustomStatusMessage.CREATE_COMMENT,commentResponseDto), CustomStatusMessage.CREATE_COMMENT.getStatus());
    }

    //댓글 수정
    @PutMapping("/comment/{id}")
    public ResponseEntity<?> updateComment (@PathVariable Long id, @RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommentResponseDto commentResponseDto = commentService.updateComment(id, requestDto, userDetails);
        return new ResponseEntity<>(BasicResponseDto.setSuccess(CustomStatusMessage.UPDATE_COMMENT, commentResponseDto), CustomStatusMessage.UPDATE_COMMENT.getStatus()) ;
    }
    //댓글 삭제
    @DeleteMapping("/comment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        CustomStatusMessage customStatusMessage = commentService.deleteComment(id, userDetails);
        return new ResponseEntity<>(BasicResponseDto.setSuccess(customStatusMessage), customStatusMessage.getStatus());
    }
    //댓글 좋아요
    @PostMapping("/comment/{id}/like")
    public ResponseEntity<?> updateLikeComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        CustomStatusMessage customStatusMessage = commentService.updateLikeComment(id, userDetails);
        return new ResponseEntity<>(BasicResponseDto.setSuccess(customStatusMessage), customStatusMessage.getStatus());
    }
}
