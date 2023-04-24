package com.sparta.board.repository;

import com.sparta.board.entity.Comment;
import com.sparta.board.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByIdAndUser(Long id, User user);

}
