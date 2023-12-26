package com.example.board_maven.service;

import com.example.board_maven.data.dto.UserRequestDto;
import org.springframework.http.ResponseEntity;

public interface MemberService {
    ResponseEntity signup(UserRequestDto formDTO);

    ResponseEntity login(UserRequestDto loginDTO);
}