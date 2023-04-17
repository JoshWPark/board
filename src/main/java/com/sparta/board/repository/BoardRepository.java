package com.sparta.board.repository;

import com.sparta.board.entity.Board;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BoardRepository {

    @PersistenceContext
    EntityManager em;

    //강의 등록
    @Transactional
    public void save(Board board) {
        //트랜잭션 시작
        em.persist(board);
        //트랜잭션 끝
    }

    //강의 단일 조회
    @Transactional
    public Optional<Board> findById(Long id) {
        Board board = em.find(Board.class, id);
        return Optional.ofNullable(board);
    }

    //강의 전체 조회
    @Transactional
    public List<Board> findAll() {
        return em.createQuery("select c from Board c", Board.class).getResultList();
    }

    //강의 삭제
    @Transactional
    public void delete(Long id) {
        // 삭제할 강의 조회
        Board course = em.find(Board.class, id);
        em.remove(course);
    }
}
