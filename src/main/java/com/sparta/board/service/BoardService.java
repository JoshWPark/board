package com.sparta.board.service;

import com.sparta.board.dto.BasicResponseDto;
import com.sparta.board.dto.board.BoardRequestDto;
import com.sparta.board.dto.board.BoardResponseDto;
import com.sparta.board.entity.Board;
import com.sparta.board.entity.StatusErrorMessageEnum;
import com.sparta.board.entity.User;
import com.sparta.board.repository.BoardRepository;
import com.sparta.board.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class BoardService extends SuperService{

    private final BoardRepository boardRepository;

    //게시물 생성
    @Transactional
    public BasicResponseDto createBoard(BoardRequestDto requestDto,User user){
        // 요청받은 DTO 로 DB에 저장할 객체 만들기
        Board board = boardRepository.saveAndFlush(Board.saveBoard(requestDto, user));

        return BasicResponseDto.setSuccess(StatusErrorMessageEnum.CREATE_BOARD.getMessage(),new BoardResponseDto(board));
    }



    // 전체 게시물 조회
    public BasicResponseDto getBoardList() {
        return BasicResponseDto.setSuccess(StatusErrorMessageEnum.GET_ALL_BOARD.getMessage(),
                boardRepository.findAll().stream().sorted(Comparator.comparing(Board::getCreatedAt).reversed()).map(BoardResponseDto::new).toList()
        );
    }

    //특정 게시물 조회
    public BasicResponseDto getBoard(Long id){
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new NullPointerException(StatusErrorMessageEnum.BOARD_NOT_EXIST.getMessage())
        );

        return BasicResponseDto.setSuccess(StatusErrorMessageEnum.GET_BOARD.getMessage(), new BoardResponseDto(board));
    }

    // 게시물 수정
    @Transactional
    public BasicResponseDto updateBoard (Long id,  BoardRequestDto requestDto,  UserDetailsImpl userDetails) {

        Board board = boardCheck(id,userDetails,boardRepository);

        board.updateBoard(requestDto);

        return BasicResponseDto.setSuccess(StatusErrorMessageEnum.UPDATE_BOARD.getMessage(),new BoardResponseDto(board));
    }

    //게시물 삭제
    @Transactional
    public BasicResponseDto deleteBoard(Long id, UserDetailsImpl userDetails){
        Board board = boardCheck(id,userDetails,boardRepository);

        boardRepository.delete(board);

        return BasicResponseDto.setSuccess(StatusErrorMessageEnum.DELETE_BOARD.getMessage());

    }
}
