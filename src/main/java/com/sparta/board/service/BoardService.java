package com.sparta.board.service;

import com.sparta.board.dto.BoardRequestDto;
import com.sparta.board.dto.BoardResponseDto;
import com.sparta.board.entity.Board;
import com.sparta.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository){
        this.boardRepository = boardRepository;
    }

    public String createBoard(BoardRequestDto requestDto){
        Board board = new Board(requestDto);
        boardRepository.save(board);
        return "게시물 저장에 성공했습니다.";
    }

    public List<BoardResponseDto> getBoardList() {
        return boardRepository.findAll().stream().map(BoardResponseDto::new).collect(Collectors.toList());
    }

    public BoardResponseDto getBoard( Long id){
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new NullPointerException("선택한 게시물이 존재하지 않습니다.")
        );

        return new BoardResponseDto(board);
    }

    public BoardResponseDto updateBoard (Long id,  BoardRequestDto requestDto) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new NullPointerException("선택한 게시물이 존재하지 않습니다.")
        );

        board.update(requestDto);

        return new BoardResponseDto(board);
    }

    public String deleteBoard(Long id){
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new NullPointerException("선택한 게시물이 존재하지 않습니다.")
        );

        boardRepository.delete(id);
        return "게시물 삭제에 성공 했습니다.";
    }
}
