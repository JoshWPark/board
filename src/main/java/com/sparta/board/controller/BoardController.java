package com.sparta.board.controller;

import com.sparta.board.dto.BasicResponseDto;
import com.sparta.board.dto.board.BoardRequestDto;
import com.sparta.board.dto.board.BoardResponseDto;
import com.sparta.board.util.CustomStatusMessage;
import com.sparta.board.security.UserDetailsImpl;
import com.sparta.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
// Client <-Dto-> Controller <-Dto-> Service <-Dto-> Repository <-Entity-> DB

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardController {

    private final BoardService boardService;

    //게시물 작성
    @PostMapping("/post")
    public ResponseEntity<?> createBoard(@RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        BoardResponseDto boardResponseDto = boardService.createBoard(requestDto, userDetails.getUser());
        return new ResponseEntity<>(BasicResponseDto.setSuccess(CustomStatusMessage.CREATE_BOARD, boardResponseDto), CustomStatusMessage.CREATE_BOARD.getStatus());
    }

    // 전체 게시물 조회
    @GetMapping("/all-posts")
    public ResponseEntity<?> getBoardList() {
        List<BoardResponseDto> boardResponseDtoList = boardService.getBoardList();
        return new ResponseEntity<>(BasicResponseDto.setSuccess(CustomStatusMessage.GET_ALL_BOARD, boardResponseDtoList), CustomStatusMessage.GET_ALL_BOARD.getStatus());
    }

    // 특정 게시물 조회
    @GetMapping("/post/{id}")
    public ResponseEntity<?> getBoard(@PathVariable Long id){
        return new ResponseEntity<>(BasicResponseDto.setSuccess(CustomStatusMessage.GET_BOARD, boardService.getBoard(id)), CustomStatusMessage.GET_BOARD.getStatus());
    }

    //게시물 수정
    @PutMapping("/post/{id}")
    public ResponseEntity<?> updateBoard (@PathVariable Long id, @RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        BoardResponseDto boardResponseDto = boardService.updateBoard(id, requestDto, userDetails);
        return new ResponseEntity<>(BasicResponseDto.setSuccess(CustomStatusMessage.UPDATE_BOARD, boardResponseDto), CustomStatusMessage.UPDATE_BOARD.getStatus());
    }

    //게시물 삭제
    @DeleteMapping("/post/{id}")
    public ResponseEntity<?> deleteBoard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        CustomStatusMessage customStatusMessage = boardService.deleteBoard(id, userDetails);
        return new ResponseEntity<>(BasicResponseDto.setSuccess(customStatusMessage), customStatusMessage.getStatus());
    }

    //게시물 좋아요
    @PostMapping("/post/{id}/like")
    public ResponseEntity<?> updateLikeBoard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        CustomStatusMessage customStatusMessage = boardService.updateLikeBoard(id, userDetails);
        return new ResponseEntity<>(BasicResponseDto.setSuccess(customStatusMessage), customStatusMessage.getStatus());
    }

}
