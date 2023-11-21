package com.example.board_maven.service.impl;

import com.example.board_maven.data.dto.BoardRequestDto;
import com.example.board_maven.data.dto.BoardResponseDto;
import com.example.board_maven.data.dto.ChangeBoardDto;
import com.example.board_maven.data.entity.Board;
import com.example.board_maven.data.repository.BoardRepository;
import com.example.board_maven.service.BoardService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    private final Logger LOGGER = LoggerFactory.getLogger(BoardServiceImpl.class);
    private final BoardRepository boardRepository;
    ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public BoardServiceImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public BoardResponseDto savaBoard(BoardRequestDto boardRequestDto) {
        LOGGER.info("[saveBoard] boardRequestDto : {}", boardRequestDto.toString());
        Board board = modelMapper.map(boardRequestDto, Board.class);
        board.setCreatedAt(LocalDateTime.now());
        board.setUpdatedAt(LocalDateTime.now());

        Board savedBoard = boardRepository.save(board);
        savedBoard.setCreateId(board.getId());  //?
        boardRepository.save(savedBoard);

        LOGGER.info("[save] savedBoard : {}", savedBoard);

        BoardResponseDto boardResponseDto = modelMapper.map(savedBoard, BoardResponseDto.class);

        return boardResponseDto;
    }

    @Override
    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    @Override
    public BoardResponseDto findById(Long id) {
        LOGGER.info("[getBoard] input id : {}", id);

        Board board = boardRepository.findById(id).get();

        return modelMapper.map(board, BoardResponseDto.class);
    }

    @Override
    public BoardResponseDto updateBoard(ChangeBoardDto changeBoardDto) {
        LOGGER.info("[updateBoard] boardRequestDto : {}", changeBoardDto.toString());
        Board foundBoard = boardRepository.findById(changeBoardDto.getId()).get();

        foundBoard.setTitle(changeBoardDto.getTitle());
        foundBoard.setContents(changeBoardDto.getContents());
        foundBoard.setUseYn(changeBoardDto.getUseYn());
        foundBoard.setUpdatedAt(LocalDateTime.now());

        Board changedBoard = boardRepository.save(foundBoard);

        LOGGER.info("[updateBoard] changedBoard : {}", changedBoard);

        BoardResponseDto boardResponseDto = modelMapper.map(changedBoard, BoardResponseDto.class);

        return boardResponseDto;
    }

    @Override
    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }
}
