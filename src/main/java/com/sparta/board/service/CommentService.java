package com.sparta.board.service;

import com.sparta.board.dto.BasicResponseDto;
import com.sparta.board.dto.board.BoardRequestDto;
import com.sparta.board.dto.comment.CommentRequestDto;
import com.sparta.board.dto.comment.CommentResponseDto;
import com.sparta.board.entity.Board;
import com.sparta.board.entity.Comment;
import com.sparta.board.entity.StatusErrorMessageEnum;
import com.sparta.board.entity.User;
import com.sparta.board.repository.BoardRepository;
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
    public BasicResponseDto updateComment(Long id, BoardRequestDto requestDto, UserDetailsImpl userDetails) {
        Comment comment = commentCheck(id,userDetails,commentRepository);

        comment.updateComment(requestDto);

        return BasicResponseDto.setSuccess(StatusErrorMessageEnum.UPDATE_COMMENT.getMessage(),new CommentResponseDto(comment));
    }

    @Transactional
    public BasicResponseDto deleteComment(Long id, UserDetailsImpl userDetails) {
        Comment comment = commentCheck(id,userDetails,commentRepository);

        commentRepository.delete(comment);

        return BasicResponseDto.setSuccess(StatusErrorMessageEnum.DELETE_COMMENT.getMessage());
    }
}
