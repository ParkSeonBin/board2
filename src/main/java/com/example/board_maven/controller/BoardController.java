package com.example.board_maven.controller;

import com.example.board_maven.data.dto.BoardRequestDto;
import com.example.board_maven.data.dto.BoardResponseDto;
import com.example.board_maven.data.dto.ChangeBoardDto;
import com.example.board_maven.data.entity.Board;
import com.example.board_maven.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {this.boardService = boardService;}

    @GetMapping(value = "/find")
    public ResponseEntity<BoardResponseDto> getBoard(Long id) {
        BoardResponseDto BoardResponseDto = boardService.findById(id);

        return ResponseEntity.status(HttpStatus.OK).body(BoardResponseDto);
    }

    @GetMapping(value = "/findAll")
    public List<Board> getBoardAll() {return boardService.findAll();}

    @PostMapping(value = "/save")
    public ResponseEntity<BoardResponseDto> createPatient(@RequestBody BoardRequestDto boardRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(boardService.savaBoard(boardRequestDto));
    }

    @PutMapping(value = "/update")
    public ResponseEntity<BoardResponseDto> changePatient(@RequestBody ChangeBoardDto changeBoardDto) {
        return ResponseEntity.status(HttpStatus.OK).body(boardService.updateBoard(changeBoardDto));

    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<String> deletePatient(Long id){
        boardService.deleteBoard(id);
        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 삭제되었습니다.");
    }
}
