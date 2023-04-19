package com.sparta.board.service;

import com.sparta.board.dto.BoardRequestDto;
import com.sparta.board.dto.BoardResponseDto;
import com.sparta.board.entity.Board;
import com.sparta.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;


    public BoardResponseDto createBoard(BoardRequestDto requestDto){
        Board board = new Board(requestDto);
        boardRepository.save(board);
        return new BoardResponseDto(board);
    }

    public List<BoardResponseDto> getBoardList() {
        return boardRepository.findAll().stream().sorted(Comparator.comparing(Board::getModifiedAt).reversed()).map(BoardResponseDto::new).collect(Collectors.toList());
    }

    public BoardResponseDto getBoard(Long id){
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new NullPointerException("선택한 게시물이 존재하지 않습니다.")
        );

        return new BoardResponseDto(board);
    }

    @Transactional //추적하기
    public BoardResponseDto updateBoard (Long id,  BoardRequestDto requestDto) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new NullPointerException("선택한 게시물이 존재하지 않습니다.")
        );
        if(board.getPassword().equals(requestDto.getPassword())){
            board.update(requestDto);
            return new BoardResponseDto(board);
        }
        else {
            return new BoardResponseDto();
        }
    }

    public String deleteBoard(Long id, BoardRequestDto requestDto){
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new NullPointerException("선택한 게시물이 존재하지 않습니다.")
        );
        if(board.getPassword().equals(requestDto.getPassword())){
            boardRepository.delete(board);
            return "게시물 삭제에 성공 했습니다.";
        }
        else {
            return "게시물 비밀번호가 틀렸습니다.";
        }
    }
}
