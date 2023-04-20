package com.sparta.board.service;

import com.sparta.board.dto.AuthRequestDto;
import com.sparta.board.dto.AuthResponseDto;
import com.sparta.board.entity.User;
import com.sparta.board.jwt.JwtUtil;
import com.sparta.board.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public AuthResponseDto signup(AuthRequestDto requestDto) {
        // 회원 중복 확인
        Optional<User> found = userRepository.findByUsername(requestDto.getUsername());
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // 사용자 DB에 저장
        userRepository.saveAndFlush(User.saveUser(requestDto));

        return new AuthResponseDto("회원가입 성공", 200);
    }

    @Transactional(readOnly = true)
    public AuthResponseDto login(AuthRequestDto requestDto, HttpServletResponse response) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );
        // 비밀번호 확인
        if(!user.getPassword().equals(password)){
            throw  new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername()));
        return new AuthResponseDto("로그인 성공", 200);
    }
}
