package com.example.board_maven.data.repository;

import com.example.board_maven.data.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
//    Board findByCreateId(String createId);
}
