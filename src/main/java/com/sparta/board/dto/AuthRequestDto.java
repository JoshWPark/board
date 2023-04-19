package com.sparta.board.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
public class AuthRequestDto {
    @Pattern(regexp = "^[a-z0-9]{4,10}$",message = "아이디는 4자이상 10자이하")
    private String username;
    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$",message = "비밀번호 8자이상 15자이하")
    private String password;
}