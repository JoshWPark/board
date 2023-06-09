package com.sparta.board.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthRequestDto {
    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)[a-z\\d]{4,10}$",message = "아이디는 최소 4자 이상 10자 이하이며 알파벳 소문자와 숫자로 구성되어야 합니다.")
    private String username;
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@!%*?&])[A-Za-z\\d$@!%*?&]{8,15}$",message = "비밀번호는 최소 8자 이상 15자 이하이며 알파벳 대소문자, 숫자와 특수문자로 구성되어야 합니다.")
    private String password;
    private boolean admin = false;
    private String adminToken = "";
}
