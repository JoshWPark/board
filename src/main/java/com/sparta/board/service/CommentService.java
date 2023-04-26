package com.sparta.board.service;

import com.sparta.board.dto.BasicResponseDto;
import com.sparta.board.dto.board.BoardRequestDto;
import com.sparta.board.dto.comment.CommentRequestDto;
import com.sparta.board.dto.comment.CommentResponseDto;
import com.sparta.board.entity.*;
import com.sparta.board.jwt.JwtUtil;
import com.sparta.board.repository.BoardRepository;
import com.sparta.board.repository.CommentRepository;
import com.sparta.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final JwtUtil jwtUtil;


    @Transactional
    public BasicResponseDto createComment(CommentRequestDto requestDto, User user) {
        // requestDto 에서 가져온 사용자 정보를 사용하여 DB 조회
        Board board = boardRepository.findById(requestDto.getPost_id()).orElseThrow(
                () -> new IllegalArgumentException(StatusErrorMessageEnum.BOARD_NOT_EXIST.getMessage())
        );

        // 요청받은 DTO 로 DB에 저장할 객체 만들기
        Comment comment = commentRepository.saveAndFlush(Comment.saveComment(requestDto,user,board));

        board.addComment(comment);

        return BasicResponseDto.setSuccess(StatusErrorMessageEnum.CREATE_COMMENT.getMessage(),new CommentResponseDto(comment));
    }
    @Transactional
    public BasicResponseDto updateComment(Long id, BoardRequestDto requestDto, User user) {
        Comment comment;
        UserRoleEnum userRoleEnum = user.getRole();
        if(userRoleEnum.equals(UserRoleEnum.USER)){
            comment = commentRepository.findByIdAndUser(id, user).orElseThrow(
                    () -> new NullPointerException(StatusErrorMessageEnum.COMMENT_NOT_EXIST_OR_WRONG_USER.getMessage())
            );
        }
        else {
            comment = commentRepository.findById(id).orElseThrow(
                    ()-> new NullPointerException(StatusErrorMessageEnum.COMMENT_NOT_EXIST.getMessage())
            );
        }

        comment.updateComment(requestDto);

        return BasicResponseDto.setSuccess(StatusErrorMessageEnum.UPDATE_COMMENT.getMessage(),new CommentResponseDto(comment));
    }

    @Transactional
    public BasicResponseDto deleteComment(Long id, User user) {
        Comment comment;
        UserRoleEnum userRoleEnum = user.getRole();
        if(userRoleEnum.equals(UserRoleEnum.USER)){
            comment = commentRepository.findByIdAndUser(id, user).orElseThrow(
                    () -> new NullPointerException(StatusErrorMessageEnum.COMMENT_NOT_EXIST_OR_WRONG_USER.getMessage())
            );
        }
        else {
            comment = commentRepository.findById(id).orElseThrow(
                    ()-> new NullPointerException(StatusErrorMessageEnum.COMMENT_NOT_EXIST.getMessage())
            );
        }

        commentRepository.delete(comment);

        return BasicResponseDto.setSuccess(StatusErrorMessageEnum.DELETE_COMMENT.getMessage());
    }
}
