package com.example.board_maven.service;

import com.example.board_maven.data.dto.BoardRequestDto;
import com.example.board_maven.data.dto.ListDto;
import com.example.board_maven.data.dto.UpdateDto;
import com.example.board_maven.data.dto.UpdateFormDto;
import com.example.board_maven.data.entity.DetailDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BoardService {
    ResponseEntity save(BoardRequestDto formDTO);

    List<ListDto> getAll();

    DetailDto getDetail(Long id, String memberId);

    ResponseEntity remove(Long id);

    UpdateDto getUpdateDto(Long id);

    ResponseEntity update(Long id, UpdateFormDto updateFormDto);
}
