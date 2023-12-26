package com.example.board_maven.data.repository;

import com.example.board_maven.data.entity.BoardUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<BoardUser, Long> {
    Optional<BoardUser> findByUserId(String uId);
}
