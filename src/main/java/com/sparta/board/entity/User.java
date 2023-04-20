package com.sparta.board.entity;

import com.sparta.board.dto.AuthRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "users")
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
//    @Pattern(regexp = "[a-z0-9]")
    private String username;
    @Column(nullable = false)
//    @Pattern(regexp = "[a-zA-Z0-9]")
    private String password;

    private User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static User saveUser(AuthRequestDto requestDto){
        return new User(requestDto.getUsername(), requestDto.getPassword());
    }
}
