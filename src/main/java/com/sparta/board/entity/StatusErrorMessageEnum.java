package com.sparta.board.entity;

public enum StatusErrorMessageEnum {
    //Status Messages
    SUCCESS_SIGNUP("회원가입 성공"),
    SUCCESS_LOGIN("로그인 성공"),
    DELETE_BOARD("게시물 삭제 성공"),
    //Error Messages
    TOKEN_ERROR("토큰이 유효하지 않습니다."),
    USER_EXIST("중복된 username 입니다."),
    USER_NOT_EXIST("회원을 찾을 수 없습니다"),
    WRONG_ADMIN_KEY("관리자 암호가 틀렸습니다."),
    WRONG_PASSWORD("회원을 찾을 수 없습니다"),
    BOARD_NOT_EXIST("해당 게시물은 존재하지 않습니다."),
    BOARD_NOT_EXIST_OR_WRONG_USER("작성자만 삭제 / 수정 할 수 있습니다.");

    private final String message;

    StatusErrorMessageEnum(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
