package com.example.board_maven.data.repository;

import com.example.board_maven.data.entity.BoardUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<BoardUser, Long> {
    BoardUser findByUserId(String userId);
}
