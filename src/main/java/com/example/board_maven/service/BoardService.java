package com.example.board_maven.service;

import com.example.board_maven.data.dto.BoardRequestDto;
import com.example.board_maven.data.dto.BoardResponseDto;
import com.example.board_maven.data.dto.ChangeBoardDto;
import com.example.board_maven.data.entity.Board;

import java.util.List;

public interface BoardService {
    BoardResponseDto savaBoard(BoardRequestDto boardRequestDto);

    List<Board> findAll();

    BoardResponseDto findById(Long id);

    BoardResponseDto updateBoard(ChangeBoardDto changeBoardDto);

    void deleteBoard(Long id);
}
