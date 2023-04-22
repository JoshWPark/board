package com.sparta.board.service;

import com.sparta.board.dto.BoardRequestDto;
import com.sparta.board.dto.BoardResponseDto;
import com.sparta.board.dto.StatusResponseDto;
import com.sparta.board.entity.Board;
import com.sparta.board.entity.StatusErrorMessageEnum;
import com.sparta.board.entity.User;
import com.sparta.board.jwt.JwtUtil;
import com.sparta.board.repository.BoardRepository;
import com.sparta.board.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    //게시물 생성
    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto requestDto, HttpServletRequest request){
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

            // 요청받은 DTO 로 DB에 저장할 객체 만들기
            Board board = boardRepository.saveAndFlush(Board.saveBoard(requestDto, user));

            return new BoardResponseDto(board);
        } else {
            throw new NullPointerException(StatusErrorMessageEnum.TOKEN_ERROR.getMessage());
        }
    }



    // 전체 게시물 조회
    public List<BoardResponseDto> getBoardList() {
        return boardRepository.findAll().stream().sorted(Comparator.comparing(Board::getCreatedAt).reversed()).map(BoardResponseDto::new).collect(Collectors.toList());
    }

    //특정 게시물 조회
    public BoardResponseDto getBoard(Long id){
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new NullPointerException(StatusErrorMessageEnum.BOARD_NOT_EXIST.getMessage())
        );

        return new BoardResponseDto(board);
    }

    // 게시물 수정
    @Transactional
    public BoardResponseDto updateBoard (Long id,  BoardRequestDto requestDto,  HttpServletRequest request) {
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

            Board board = boardRepository.findByIdAndUser(id, user).orElseThrow(
                    () -> new NullPointerException(StatusErrorMessageEnum.BOARD_NOT_EXIST_OR_WRONG_USER.getMessage())
            );

            board.updateBoard(requestDto);

            return new BoardResponseDto(board);

        } else {
            return null;
        }


    }

    //게시물 삭제
    @Transactional
    public StatusResponseDto deleteBoard(Long id, HttpServletRequest request){
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 게시물 삭제 가능
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
                    () -> new NullPointerException(StatusErrorMessageEnum.USER_NOT_EXIST.getMessage())
            );

            Board board = boardRepository.findByIdAndUser(id, user).orElseThrow(
                    () -> new NullPointerException(StatusErrorMessageEnum.BOARD_NOT_EXIST_OR_WRONG_USER.getMessage())
            );

            boardRepository.delete(board);

            return new StatusResponseDto(StatusErrorMessageEnum.DELETE_BOARD.getMessage(), 200);

        } else {
            return null;
        }

    }
}
