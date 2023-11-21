package com.example.board_maven.data.repository;

import com.example.board_maven.data.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Board findByTitle(String title);
}
