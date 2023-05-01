package com.sparta.board.exception;

import org.springframework.http.HttpStatus;

public enum CustomStatusMessage {
    //Status Messages
    SUCCESS_SIGNUP("회원가입 성공", HttpStatus.OK),
    SUCCESS_LOGIN("로그인 성공", HttpStatus.OK),
    CREATE_BOARD("게시물 생성 성공", HttpStatus.OK),
    GET_ALL_BOARD("전체 게시물 조회 성공", HttpStatus.OK),
    GET_BOARD("게시물 조회 성공", HttpStatus.OK),
    UPDATE_BOARD("게시물 수정 성공", HttpStatus.OK),
    DELETE_BOARD("게시물 삭제 성공", HttpStatus.OK),
    CREATE_COMMENT("댓글 생성 성공", HttpStatus.OK),
    UPDATE_COMMENT("댓글 수정 성공", HttpStatus.OK),
    DELETE_COMMENT("댓글 삭제 성공", HttpStatus.OK),
    LIKE_BOARD("게시물 좋아요 성공", HttpStatus.OK),
    UNLIKE_BOARD("게시물 좋아요 취소 성공", HttpStatus.OK),
    LIKE_COMMENT("댓글 좋아요 성공", HttpStatus.OK),
    UNLIKE_COMMENT("댓글 좋아요 취소 성공", HttpStatus.OK),
    //Error Messages
    TOKEN_ERROR("토큰이 유효하지 않습니다.", HttpStatus.BAD_REQUEST),
    USER_EXIST("중복된 username 입니다.", HttpStatus.BAD_REQUEST),
    USER_NOT_EXIST("회원을 찾을 수 없습니다", HttpStatus.BAD_REQUEST),
    WRONG_ADMIN_KEY("관리자 암호가 틀렸습니다.", HttpStatus.BAD_REQUEST),
    WRONG_PASSWORD("회원을 찾을 수 없습니다", HttpStatus.BAD_REQUEST),
    BOARD_NOT_EXIST("해당 게시물은 존재하지 않습니다.", HttpStatus.BAD_REQUEST),
    BOARD_NOT_EXIST_OR_WRONG_USER("해당 게시물이 존재하지 않거나 작성자만 삭제 / 수정 할 수 있습니다.", HttpStatus.BAD_REQUEST ),
    COMMENT_NOT_EXIST("해당 댓글은 존재하지 않습니다.", HttpStatus.BAD_REQUEST),
    COMMENT_NOT_EXIST_OR_WRONG_USER("해당 댓글이 존재하지 않거나 작성자만 삭제 / 수정 할 수 있습니다.", HttpStatus.BAD_REQUEST);


    private final String message;
    private final HttpStatus status;

    CustomStatusMessage(String message, HttpStatus status){
        this.message = message;
        this.status = status;
    }

    public String getMessage(){
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
