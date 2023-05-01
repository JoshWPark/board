package com.sparta.board.exception;

import com.sparta.board.util.CustomStatusMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomError extends RuntimeException{
    private final CustomStatusMessage customStatusMessage;
}
