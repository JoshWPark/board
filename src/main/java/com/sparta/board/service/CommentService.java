package com.sparta.board.service;

import com.sparta.board.dto.board.BoardRequestDto;
import com.sparta.board.dto.comment.CommentRequestDto;
import com.sparta.board.dto.comment.CommentResponseDto;
import com.sparta.board.entity.*;
import com.sparta.board.repository.BoardRepository;
import com.sparta.board.repository.CommentLikeRepository;
import com.sparta.board.repository.CommentRepository;
import com.sparta.board.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService extends SuperService{

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;



    @Transactional
    public CommentResponseDto createComment(CommentRequestDto requestDto, User user) {
        // requestDto 에서 가져온 사용자 정보를 사용하여 DB 조회
        Board board = boardRepository.findById(requestDto.getPost_id()).orElseThrow(
                () -> new IllegalArgumentException(StatusErrorMessageEnum.BOARD_NOT_EXIST.getMessage())
        );

        // 요청받은 DTO 로 DB에 저장할 객체 만들기
        Comment comment = commentRepository.saveAndFlush(Comment.saveComment(requestDto,user,board));

        board.addComment(comment);

        return new CommentResponseDto(comment);
    }
    @Transactional
    public CommentResponseDto updateComment(Long id, BoardRequestDto requestDto, UserDetailsImpl userDetails) {
        Comment comment = commentCheck(id,userDetails,commentRepository);

        comment.updateComment(requestDto);

        return new CommentResponseDto(comment);
    }

    @Transactional
    public String deleteComment(Long id, UserDetailsImpl userDetails) {
        Comment comment = commentCheck(id,userDetails,commentRepository);

        commentRepository.delete(comment);

        return StatusErrorMessageEnum.DELETE_COMMENT.getMessage();
    }

    @Transactional
    public String updateLikeComment(Long id, UserDetailsImpl userDetails){
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new NullPointerException(StatusErrorMessageEnum.COMMENT_NOT_EXIST.getMessage())
        );
        if(commentLikeRepository.findByCommentAndUser(comment, userDetails.getUser()) == null){

            commentLikeRepository.saveAndFlush(CommentLike.updateLike(comment, userDetails));
            comment.updateLike(true);
            return StatusErrorMessageEnum.LIKE_COMMENT.getMessage();
        }
        else{
            CommentLike commentLike = commentLikeRepository.findByCommentAndUser(comment, userDetails.getUser());
            comment.updateLike(false);
            commentLikeRepository.delete(commentLike);
            return StatusErrorMessageEnum.UNLIKE_COMMENT.getMessage();
        }
    }
}
