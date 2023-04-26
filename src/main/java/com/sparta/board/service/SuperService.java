package com.sparta.board.service;

import com.sparta.board.entity.Board;
import com.sparta.board.entity.Comment;
import com.sparta.board.entity.StatusErrorMessageEnum;
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
                    ()-> new NullPointerException(StatusErrorMessageEnum.COMMENT_NOT_EXIST.getMessage())
            );
        }
        else {
            comment = commentRepository.findByIdAndUser(id, userDetails.getUser()).orElseThrow(
                    () -> new NullPointerException(StatusErrorMessageEnum.COMMENT_NOT_EXIST_OR_WRONG_USER.getMessage())
            );
        }
        return comment;
    }

    public Board boardCheck(Long id, UserDetailsImpl userDetails, BoardRepository boardRepository){
        Board board;
        if(isAdmin(userDetails)){
            board = boardRepository.findById(id).orElseThrow(
                    ()-> new NullPointerException(StatusErrorMessageEnum.COMMENT_NOT_EXIST.getMessage())
            );
        }
        else {
            board = boardRepository.findByIdAndUser(id, userDetails.getUser()).orElseThrow(
                    () -> new NullPointerException(StatusErrorMessageEnum.COMMENT_NOT_EXIST_OR_WRONG_USER.getMessage())
            );
        }
        return board;
    }

}
