package com.sparta.board.controller;

import com.sparta.board.dto.BoardRequestDto;
import com.sparta.board.dto.BoardResponseDto;
import com.sparta.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping("/create")
    public String createBoard(@RequestBody BoardRequestDto requestDto){
        return boardService.createBoard(requestDto);
    }

    @GetMapping("/list")
    public List<BoardResponseDto> getBoardList() {
        return boardService.getBoardList();
    }

    @GetMapping("/{id}") // http://localhost:8080/course/{id}
    public BoardResponseDto getBoard(@PathVariable Long id){
        return boardService.getBoard(id);
    }

    @PutMapping("/update/{id}")
    public BoardResponseDto updateBoard (@PathVariable Long id, @RequestBody BoardRequestDto requestDto) {
        return boardService.updateBoard(id,requestDto);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteBoard(@PathVariable Long id){
        return boardService.deleteBoard(id);
    }


}
