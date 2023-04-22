package com.sparta.board.repository;

import com.sparta.board.entity.Board;
import com.sparta.board.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findByIdAndUser(Long id, User user);
}
