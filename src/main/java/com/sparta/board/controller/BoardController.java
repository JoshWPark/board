package com.sparta.board.controller;

import com.sparta.board.dto.BasicResponseDto;
import com.sparta.board.dto.board.BoardRequestDto;
import com.sparta.board.security.UserDetailsImpl;
import com.sparta.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
// Client <-Dto-> Controller <-Dto-> Service <-Dto-> Repository <-Entity-> DB

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    //게시물 작성
    @PostMapping("/new")
    public BasicResponseDto createBoard(@RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return boardService.createBoard(requestDto, userDetails.getUser());
    }

    // 전체 게시물 조회
    @GetMapping("/post")
    public BasicResponseDto getBoardList() {
        return boardService.getBoardList();
    }

    // 특정 게시물 조회
    @GetMapping("/post/{id}")
    public BasicResponseDto getBoard(@PathVariable Long id){
        return boardService.getBoard(id);
    }

    //게시물 수정
    @PutMapping("/post/{id}")
    public BasicResponseDto updateBoard (@PathVariable Long id, @RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.updateBoard(id, requestDto, userDetails);
    }

    //게시물 삭제
    @DeleteMapping("/post/{id}")
    public BasicResponseDto deleteBoard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return boardService.deleteBoard(id, userDetails);
    }


}
