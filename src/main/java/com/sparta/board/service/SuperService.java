package com.sparta.board.service;

import com.sparta.board.entity.Board;
import com.sparta.board.entity.Comment;
import com.sparta.board.exception.CustomError;
import com.sparta.board.util.CustomStatusMessage;
import com.sparta.board.entity.UserRoleEnum;
import com.sparta.board.repository.BoardRepository;
import com.sparta.board.repository.CommentRepository;
import com.sparta.board.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SuperService {

    private boolean isAdmin (UserDetailsImpl userDetails){
        return userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(UserRoleEnum.ADMIN.getAuthority()));
    }

    public Comment commentCheck(Long id, UserDetailsImpl userDetails, CommentRepository commentRepository){
        Comment comment;
        if(isAdmin(userDetails)){
            comment = commentRepository.findById(id).orElseThrow(
                    ()-> new CustomError(CustomStatusMessage.COMMENT_NOT_EXIST)
            );
        }
        else {
            comment = commentRepository.findByIdAndUser(id, userDetails.getUser()).orElseThrow(
                    () -> new CustomError(CustomStatusMessage.COMMENT_NOT_EXIST_OR_WRONG_USER)
            );
        }
        return comment;
    }

    public Board boardCheck(Long id, UserDetailsImpl userDetails, BoardRepository boardRepository){
        Board board;
        if(isAdmin(userDetails)){
            board = boardRepository.findById(id).orElseThrow(
                    ()-> new CustomError(CustomStatusMessage.BOARD_NOT_EXIST)
            );
        }
        else {
            board = boardRepository.findByIdAndUser(id, userDetails.getUser()).orElseThrow(
                    () -> new CustomError(CustomStatusMessage.BOARD_NOT_EXIST_OR_WRONG_USER)
            );
        }
        return board;
    }

}
