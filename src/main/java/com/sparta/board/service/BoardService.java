package com.sparta.board.service;

import com.sparta.board.dto.BoardRequestDto;
import com.sparta.board.dto.BoardResponseDto;
import com.sparta.board.entity.Board;
import com.sparta.board.entity.User;
import com.sparta.board.jwt.JwtUtil;
import com.sparta.board.repository.BoardRepository;
import com.sparta.board.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    //게시물 생성
    public BoardResponseDto createBoard(BoardRequestDto requestDto, HttpServletRequest request){
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 관심상품 추가 가능
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            // 요청받은 DTO 로 DB에 저장할 객체 만들기
            Board board = boardRepository.saveAndFlush(new Board(requestDto,user.getUsername()));

            return new BoardResponseDto(board);
        } else {
            return null;
        }

    }



    // 전체 게시물 조회
    public List<BoardResponseDto> getBoardList() {
        return boardRepository.findAll().stream().sorted(Comparator.comparing(Board::getModifiedAt).reversed()).map(BoardResponseDto::new).collect(Collectors.toList());
    }

    //특정 게시물 조회
    public BoardResponseDto getBoard(Long id){
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new NullPointerException("선택한 게시물이 존재하지 않습니다.")
        );

        return new BoardResponseDto(board);
    }

    // 게시물 수정
//    @Transactional //추적하기
//    public BoardResponseDto updateBoard (Long id,  BoardRequestDto requestDto) {
//        Board board = boardRepository.findById(id).orElseThrow(
//                () -> new NullPointerException("선택한 게시물이 존재하지 않습니다.")
//        );
//        if(board.getPassword().equals(requestDto.getPassword())){
//            board.update(requestDto);
//            return new BoardResponseDto(board);
//        }
//        else {
//            return new BoardResponseDto();
//        }
//    }

    //게시물 삭제
//    public String deleteBoard(Long id, BoardRequestDto requestDto){
//        Board board = boardRepository.findById(id).orElseThrow(
//                () -> new NullPointerException("선택한 게시물이 존재하지 않습니다.")
//        );
//        if(board.getPassword().equals(requestDto.getPassword())){
//            boardRepository.delete(board);
//            return "게시물 삭제에 성공 했습니다.";
//        }
//        else {
//            return "게시물 비밀번호가 틀렸습니다.";
//        }
//    }
}
