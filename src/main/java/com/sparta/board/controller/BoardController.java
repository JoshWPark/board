package com.sparta.board.controller;

import com.sparta.board.dto.BoardRequestDto;
import com.sparta.board.dto.BoardResponseDto;
import com.sparta.board.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
// Client <-Dto-> Controller <-Dto-> Service <-Dto-> Repository <-Entity-> DB

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    //게시물 작성
    @PostMapping("/post")
    public BoardResponseDto createBoard(@RequestBody BoardRequestDto requestDto, HttpServletRequest request){
        return boardService.createBoard(requestDto, request);
    }

    // 전체 게시물 조회
    @GetMapping("/posts")
    public List<BoardResponseDto> getBoardList() {
        return boardService.getBoardList();
    }

    // 특정 게시물 조회
    @GetMapping("/post/{id}")
    public BoardResponseDto getBoard(@PathVariable Long id){
        return boardService.getBoard(id);
    }

    //게시물 수정
//    @PutMapping("/post/{id}")
//    public BoardResponseDto updateBoard (@PathVariable Long id, @RequestBody BoardRequestDto requestDto) {
//        return boardService.updateBoard(id, requestDto);
//    }

    //게시물 삭제
//    @DeleteMapping("/post/{id}")
//    public String deleteBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto){
//        return boardService.deleteBoard(id, requestDto);
//    }


}
