package com.example.board_maven.service.impl;

import com.example.board_maven.data.dto.BoardRequestDto;
import com.example.board_maven.data.dto.ListDto;
import com.example.board_maven.data.dto.UpdateDto;
import com.example.board_maven.data.dto.UpdateFormDto;
import com.example.board_maven.data.entity.*;
import com.example.board_maven.data.repository.BoardRepository;
import com.example.board_maven.data.repository.MemberRepository;
import com.example.board_maven.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final Logger LOGGER = LoggerFactory.getLogger(BoardServiceImpl.class);
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    @Override
    public ResponseEntity save(BoardRequestDto formDTO) {
        Optional<BoardUser> member = memberRepository.findByUserId(formDTO.getCreateId());
        LOGGER.info("[formDTO.getCreateId()]  : {}", formDTO.getCreateId());
        if(member.isPresent()){
            BoardUser memberEntity = member.get();

            Board post = Board.builder()
                    .title(formDTO.getTitle())
                    .content(formDTO.getContent())
                    .useYn(useYn.Y)
                    .createId(memberEntity)
                    .build();
            LOGGER.info("[save before] savedBoard : {}", post);

            boardRepository.save(post);
            LOGGER.info("[save] savedBoard : {}", post);
            return new ResponseEntity("success", HttpStatus.OK);

        } else {
            return new ResponseEntity("fail", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public List<ListDto> getAll() {
        List<Board> posts = boardRepository.findAll();
        List<ListDto> list = new ArrayList<>();

        LOGGER.info("[boardRepository.findAll()] : {}", posts);

        for (Board post : posts) {
            BoardUser member = post.getCreateId();
            LOGGER.info("[post.getCreateId()] : {}", member);

            ListDto dto = ListDto.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .createdAt(post.getCreatedAt())
                    .createId(member.getUserId())
                    .build();

            list.add(dto);
        }
        return list;
    }

    @Override
    public DetailDto getDetail(Long id, String memberId) {
        Optional<Board> board = boardRepository.findById(id);
        Board boardEntity = board.orElse(null);

        BoardUser member = boardEntity.getCreateId();
        LOGGER.info("[boardEntity.getCreateId()] : {}", member);

        DetailDto detailDTO = DetailDto.builder()
                .id(boardEntity.getId())
                .title(boardEntity.getTitle())
                .content(boardEntity.getContent())
                .createdAt(boardEntity.getCreatedAt())
                .updatedAt(boardEntity.getUpdatedAt())
                .createId(member.getUserId())
                .build();

        LOGGER.info("[detailDTO] : {}", detailDTO);

        return detailDTO;
    }

    @Override
    public ResponseEntity remove(Long id) {
        boardRepository.deleteById(id);
        return new ResponseEntity("success", HttpStatus.OK);
    }

    @Override
    public UpdateDto getUpdateDto(Long id) {
        Optional<Board> board = boardRepository.findById(id);
        Board boardEntity = board.orElseGet(null);

        UpdateDto updateDto = UpdateDto.builder()
                .id(boardEntity.getId())
                .title(boardEntity.getTitle())
                .content(boardEntity.getContent())
                .build();

        return updateDto;
    }

    @Override
    public ResponseEntity update(Long id, UpdateFormDto updateFormDto) {
        Optional<Board> board = boardRepository.findById(id);
        Board boardEntity = board.orElseGet(null);

        boardEntity.update(updateFormDto);

        return new ResponseEntity("success", HttpStatus.OK);
    }
}
