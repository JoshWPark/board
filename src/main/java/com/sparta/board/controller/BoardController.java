package com.sparta.board.controller;

import com.sparta.board.dto.BasicResponseDto;
import com.sparta.board.dto.board.BoardRequestDto;
import com.sparta.board.dto.board.BoardResponseDto;
import com.sparta.board.entity.StatusErrorMessageEnum;
import com.sparta.board.security.UserDetailsImpl;
import com.sparta.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
// Client <-Dto-> Controller <-Dto-> Service <-Dto-> Repository <-Entity-> DB

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    //게시물 작성
    @PostMapping("/new")
    public BasicResponseDto<BoardResponseDto> createBoard(@RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        BoardResponseDto boardResponseDto = boardService.createBoard(requestDto, userDetails.getUser());
        return BasicResponseDto.setSuccess(StatusErrorMessageEnum.CREATE_BOARD.getMessage(), boardResponseDto);
    }

    // 전체 게시물 조회
    @GetMapping("/post")
    public BasicResponseDto<List<BoardResponseDto>> getBoardList() {
        List<BoardResponseDto> boardResponseDtoList = boardService.getBoardList();
        return BasicResponseDto.setSuccess(StatusErrorMessageEnum.GET_ALL_BOARD.getMessage(), boardResponseDtoList);
    }

    // 특정 게시물 조회
    @GetMapping("/post/{id}")
    public BasicResponseDto<BoardResponseDto> getBoard(@PathVariable Long id){
        return BasicResponseDto.setSuccess(StatusErrorMessageEnum.GET_BOARD.getMessage(), boardService.getBoard(id));
    }

    //게시물 수정
    @PutMapping("/post/{id}")
    public BasicResponseDto<BoardResponseDto> updateBoard (@PathVariable Long id, @RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        BoardResponseDto boardResponseDto = boardService.updateBoard(id, requestDto, userDetails);
        return BasicResponseDto.setSuccess(StatusErrorMessageEnum.UPDATE_BOARD.getMessage(), boardResponseDto);
    }

    //게시물 삭제
    @DeleteMapping("/post/{id}")
    public BasicResponseDto<?> deleteBoard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        String message = boardService.deleteBoard(id, userDetails);
        return BasicResponseDto.setSuccess(message);
    }

    //게시물 좋아요
    @PostMapping("/post/like/{id}")
    public BasicResponseDto<?> updateLikeBoard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        String message = boardService.updateLikeBoard(id, userDetails);
        return BasicResponseDto.setSuccess(message);
    }

}
