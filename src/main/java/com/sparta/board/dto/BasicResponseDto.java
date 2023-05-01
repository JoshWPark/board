package com.sparta.board.dto;

import com.sparta.board.util.CustomStatusMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor (staticName = "set")
public class BasicResponseDto<T> {
    private String message;
    private int code;
    private T data;

    public static <T> BasicResponseDto<T> setSuccess(CustomStatusMessage status) {
        return BasicResponseDto.set(status.getMessage(), status.getStatus().value(), null);
    }

    public static <T> BasicResponseDto<T> setSuccess(CustomStatusMessage status, T data) {
        return BasicResponseDto.set(status.getMessage(), status.getStatus().value(),data);
    }

    public static <T> BasicResponseDto<T> setBadRequest(String message, HttpStatus code) {
        return BasicResponseDto.set(message, code.value(),null);
    }

    public static <T> BasicResponseDto<T> setBadRequest(String message, int code) {
        return BasicResponseDto.set(message, code,null);
    }
}
