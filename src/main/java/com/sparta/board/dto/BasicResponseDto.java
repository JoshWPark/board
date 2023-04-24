package com.sparta.board.dto;

import com.sparta.board.entity.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor (staticName = "set")
public class BasicResponseDto<T> {
    private String status;
    private int code;
    private T data;

    public static <T> BasicResponseDto<T> setSuccess(String status) {
        return BasicResponseDto.set(status, StatusCode.OK.getStatusCode(), null);
    }

    public static <T> BasicResponseDto<T> setSuccess(String status, T data) {
        return BasicResponseDto.set(status,StatusCode.OK.getStatusCode(),data);
    }

    public static <T> BasicResponseDto<T> setBadRequest(String status, StatusCode statusCode) {
        return BasicResponseDto.set(status, statusCode.getStatusCode(),null);
    }
}
