package com.sparta.board.service;

import com.sparta.board.dto.board.BoardRequestDto;
import com.sparta.board.dto.comment.CommentRequestDto;
import com.sparta.board.dto.comment.CommentResponseDto;
import com.sparta.board.entity.*;
import com.sparta.board.exception.CustomError;
import com.sparta.board.repository.BoardRepository;
import com.sparta.board.repository.CommentLikeRepository;
import com.sparta.board.repository.CommentRepository;
import com.sparta.board.security.UserDetailsImpl;
import com.sparta.board.util.CustomStatusMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService{

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;



    @Transactional
    public CommentResponseDto createComment(CommentRequestDto requestDto, User user) {
        // requestDto 에서 가져온 사용자 정보를 사용하여 DB 조회
        Board board = boardRepository.findById(requestDto.getPost_id()).orElseThrow(
                () -> new CustomError(CustomStatusMessage.BOARD_NOT_EXIST)
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
    public CustomStatusMessage deleteComment(Long id, UserDetailsImpl userDetails) {
        Comment comment = commentCheck(id,userDetails,commentRepository);

        commentRepository.delete(comment);

        return CustomStatusMessage.DELETE_COMMENT;
    }

    @Transactional
    public CustomStatusMessage updateLikeComment(Long id, UserDetailsImpl userDetails){
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new CustomError(CustomStatusMessage.COMMENT_NOT_EXIST)
        );
        if(commentLikeRepository.findByCommentAndUser(comment, userDetails.getUser()) == null){

            commentLikeRepository.saveAndFlush(CommentLike.updateLike(comment, userDetails));
            comment.updateLike(true);
            return CustomStatusMessage.LIKE_COMMENT;
        }
        else{
            CommentLike commentLike = commentLikeRepository.findByCommentAndUser(comment, userDetails.getUser());
            comment.updateLike(false);
            commentLikeRepository.delete(commentLike);
            return CustomStatusMessage.UNLIKE_COMMENT;
        }
    }

    private Comment commentCheck(Long id, UserDetailsImpl userDetails, CommentRepository commentRepository){
        Comment comment;
        if(isAdmin(userDetails)){
            comment = commentRepository.findById(id).orElseThrow(
                    ()-> new CustomError(CustomStatusMessage.COMMENT_NOT_EXIST)
            );
        }
        else {
            comment = commentRepository.findByIdAndUser(id, userDetails.getUser()).orElseThrow(
                    () -> new CustomError(CustomStatusMessage.COMMENT_NOT_EXIST_OR_WRONG_USER)
            );
        }
        return comment;
    }

    private boolean isAdmin (UserDetailsImpl userDetails){
        return userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(UserRoleEnum.ADMIN.getAuthority()));
    }
}
