package com.sparta.board.service;

import com.sparta.board.dto.board.BoardRequestDto;
import com.sparta.board.dto.board.BoardResponseDto;
import com.sparta.board.entity.Board;
import com.sparta.board.entity.BoardLike;
import com.sparta.board.entity.StatusErrorMessageEnum;
import com.sparta.board.entity.User;
import com.sparta.board.repository.BoardLikeRepository;
import com.sparta.board.repository.BoardRepository;
import com.sparta.board.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService extends SuperService{

    private final BoardRepository boardRepository;
    private final BoardLikeRepository boardLikeRepository;

    //게시물 생성
    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto requestDto,User user){
        // 요청받은 DTO 로 DB에 저장할 객체 만들기
        Board board = boardRepository.saveAndFlush(Board.saveBoard(requestDto, user));
        return new BoardResponseDto(board);
    }



    // 전체 게시물 조회
    public List<BoardResponseDto> getBoardList() {
        return boardRepository.findAll().stream().sorted(Comparator.comparing(Board::getCreatedAt).reversed()).map(BoardResponseDto::new).toList();
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
    public BoardResponseDto updateBoard (Long id,  BoardRequestDto requestDto,  UserDetailsImpl userDetails) {

        Board board = boardCheck(id,userDetails,boardRepository);

        board.updateBoard(requestDto);

        return new BoardResponseDto(board);
    }

    //게시물 삭제
    @Transactional
    public String deleteBoard(Long id, UserDetailsImpl userDetails){
        Board board = boardCheck(id,userDetails,boardRepository);

        boardRepository.delete(board);

        return StatusErrorMessageEnum.DELETE_BOARD.getMessage();

    }

    @Transactional
    public String updateLikeBoard(Long id,UserDetailsImpl userDetails){
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new NullPointerException(StatusErrorMessageEnum.BOARD_NOT_EXIST.getMessage())
        );
        if(boardLikeRepository.findByBoardAndUser(board, userDetails.getUser()) == null){

            boardLikeRepository.saveAndFlush(BoardLike.updateLike(board, userDetails));
            board.updateLike(true);
            return StatusErrorMessageEnum.LIKE_BOARD.getMessage();
        }
        else{
            BoardLike boardLike = boardLikeRepository.findByBoardAndUser(board, userDetails.getUser());
            board.updateLike(false);
            boardLikeRepository.delete(boardLike);
            return StatusErrorMessageEnum.UNLIKE_BOARD.getMessage();
        }
    }
}
