package com.sparta.board.service;

import com.sparta.board.dto.AuthRequestDto;
import com.sparta.board.exception.CustomError;
import com.sparta.board.util.CustomStatusMessage;
import com.sparta.board.entity.User;
import com.sparta.board.entity.UserRoleEnum;
import com.sparta.board.jwt.JwtUtil;
import com.sparta.board.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private static final String ADMIN_TOKEN = "hanghae9914GiFighthingSpringBackEnd";
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public CustomStatusMessage signup(AuthRequestDto requestDto) {
        String password = passwordEncoder.encode(requestDto.getPassword());

        // 회원 중복 확인
        Optional<User> found = userRepository.findByUsername(requestDto.getUsername());
        if (found.isPresent()) {
            throw new CustomError(CustomStatusMessage.USER_EXIST);
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = (requestDto.isAdmin()) ? UserRoleEnum.ADMIN : UserRoleEnum.USER;
        if (!requestDto.getAdminToken().equals(ADMIN_TOKEN)) {
            throw new CustomError(CustomStatusMessage.WRONG_ADMIN_KEY);
        }


        // 사용자 DB에 저장
        userRepository.saveAndFlush(User.saveUser(requestDto.getUsername(),password,role));
        return CustomStatusMessage.SUCCESS_SIGNUP;
    }

    @Transactional(readOnly = true)
    public CustomStatusMessage login(AuthRequestDto requestDto, HttpServletResponse response) {
        // 사용자 확인
        User user = userRepository.findByUsername(requestDto.getUsername()).orElseThrow(
                () -> new CustomError(CustomStatusMessage.USER_NOT_EXIST)
        );
        // 비밀번호 확인
        if(!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())){
            throw  new CustomError(CustomStatusMessage.WRONG_PASSWORD);
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
        return CustomStatusMessage.SUCCESS_LOGIN;
    }
}
