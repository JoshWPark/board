package com.sparta.board.service;

import com.sparta.board.dto.AuthRequestDto;
import com.sparta.board.dto.BasicResponseDto;
import com.sparta.board.entity.StatusErrorMessageEnum;
import com.sparta.board.entity.User;
import com.sparta.board.entity.UserRoleEnum;
import com.sparta.board.jwt.JwtUtil;
import com.sparta.board.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private static final String ADMIN_TOKEN = "hanghae9914GiFighthingSpringBackEnd";

    @Transactional
    public BasicResponseDto signup(AuthRequestDto requestDto) {
        // 회원 중복 확인
        Optional<User> found = userRepository.findByUsername(requestDto.getUsername());
        if (found.isPresent()) {
            throw new IllegalArgumentException(StatusErrorMessageEnum.USER_EXIST.getMessage());
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = (requestDto.isAdmin()) ? UserRoleEnum.ADMIN : UserRoleEnum.USER;
        if (!requestDto.getAdminToken().equals(ADMIN_TOKEN)) {
            throw new IllegalArgumentException(StatusErrorMessageEnum.WRONG_ADMIN_KEY.getMessage());
        }
        // 사용자 DB에 저장
        userRepository.saveAndFlush(User.saveUser(requestDto,role));
        return BasicResponseDto.setSuccess(StatusErrorMessageEnum.SUCCESS_SIGNUP.getMessage());
    }

    @Transactional(readOnly = true)
    public BasicResponseDto login(AuthRequestDto requestDto, HttpServletResponse response) {
        // 사용자 확인
        User user = userRepository.findByUsername(requestDto.getUsername()).orElseThrow(
                () -> new NullPointerException(StatusErrorMessageEnum.USER_NOT_EXIST.getMessage())
        );
        // 비밀번호 확인
        if(!user.getPassword().equals(requestDto.getPassword())){
            throw  new IllegalArgumentException(StatusErrorMessageEnum.WRONG_PASSWORD.getMessage());
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
        return BasicResponseDto.setSuccess(StatusErrorMessageEnum.SUCCESS_LOGIN.getMessage());
    }
}
