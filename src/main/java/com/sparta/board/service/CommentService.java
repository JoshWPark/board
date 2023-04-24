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
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
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
    public BasicResponseDto createComment(CommentRequestDto requestDto, HttpServletRequest request) {
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 게시물 추가 가능
        if (token != null) {
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException(StatusErrorMessageEnum.TOKEN_ERROR.getMessage());
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException(StatusErrorMessageEnum.USER_NOT_EXIST.getMessage())
            );

            // requestDto 에서 가져온 사용자 정보를 사용하여 DB 조회
            Board board = boardRepository.findById(requestDto.getPost_id()).orElseThrow(
                    () -> new IllegalArgumentException(StatusErrorMessageEnum.BOARD_NOT_EXIST.getMessage())
            );

            // 요청받은 DTO 로 DB에 저장할 객체 만들기
            Comment comment = commentRepository.saveAndFlush(Comment.saveComment(requestDto,user,board));

            board.addComment(comment);

            return BasicResponseDto.setSuccess(StatusErrorMessageEnum.CREATE_COMMENT.getMessage(),new CommentResponseDto(comment));
        } else {
            throw new NullPointerException(StatusErrorMessageEnum.TOKEN_ERROR.getMessage());
        }
    }
    @Transactional
    public BasicResponseDto updateComment(Long id, BoardRequestDto requestDto, HttpServletRequest request) {
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 게시물 수정 가능
        if (token != null) {
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException(StatusErrorMessageEnum.TOKEN_ERROR.getMessage());
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException(StatusErrorMessageEnum.USER_NOT_EXIST.getMessage())
            );

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

        } else {
            return null;
        }
    }

    @Transactional
    public BasicResponseDto deleteComment(Long id, HttpServletRequest request) {
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 게시물 수정 가능
        if (token != null) {
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException(StatusErrorMessageEnum.TOKEN_ERROR.getMessage());
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException(StatusErrorMessageEnum.USER_NOT_EXIST.getMessage())
            );

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

        } else {
            return null;
        }
    }
}
