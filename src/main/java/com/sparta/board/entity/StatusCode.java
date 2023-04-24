package com.sparta.board.entity;

public enum StatusCode {
    OK(200),
    BAD_REQUEST(400),
    METHOD_NOT_ALLOWED(405),
    NOT_ACCEPTABLE(406);
    private final int statusCode;

    StatusCode(int statusCode){this.statusCode = statusCode;}

    public int getStatusCode(){return statusCode;}
}
