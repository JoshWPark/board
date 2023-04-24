package com.sparta.board.entity;

public enum StatusCode {
    OK(200),
    BAD_REQUEST(400),
    INTERNAL_SERVER_ERROR(500);

    private final int statusCode;

    StatusCode(int statusCode){this.statusCode = statusCode;}

    public int getStatusCode(){return statusCode;}
}
