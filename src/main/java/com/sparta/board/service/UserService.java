package com.sparta.board.service;

import com.sparta.board.dto.SignupRequestDto;
import com.sparta.board.entity.User;
import com.sparta.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public String signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = signupRequestDto.getPassword();

        // 회원 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            return "중복된 사용자가 존재합니다.";
        }

            // 정규표현식 확인
        User user = new User(username, password);
        userRepository.save(user);

        return "회원가입 성공";
    }
}
