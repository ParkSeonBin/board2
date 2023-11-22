package com.example.board_maven.service.impl;

import com.example.board_maven.data.dto.BoardRequestDto;
import com.example.board_maven.data.dto.BoardResponseDto;
import com.example.board_maven.data.dto.ChangeBoardDto;
import com.example.board_maven.data.entity.Board;
import com.example.board_maven.data.entity.BoardUser;
import com.example.board_maven.data.repository.BoardRepository;
import com.example.board_maven.data.repository.UserRepository;
import com.example.board_maven.service.BoardService;
import com.example.board_maven.service.UserDetailsService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    private final Logger LOGGER = LoggerFactory.getLogger(BoardServiceImpl.class);
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public BoardServiceImpl(BoardRepository boardRepository, UserRepository userRepository) {
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }

    @Override
    public BoardResponseDto savaBoard(BoardRequestDto boardRequestDto) {
        LOGGER.info("[saveBoard] boardRequestDto : {}", boardRequestDto.toString());

        // Get the currently authenticated user's information
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LOGGER.info("Authentication details: {}", authentication);

        if (authentication != null) {
            LOGGER.info("Principal class: {}", authentication.getPrincipal().getClass());
        }
        // Check if the authentication object is not null and the principal is a UserDetails instance
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Assuming your UserDetails implementation has a method to get the user ID
            String currentUserId = userDetails.getUsername();

            // Retrieve the BoardUser object from your repository based on the user ID
            BoardUser currentUser = userRepository.findByUserId(currentUserId);

            // Create a new board
            Board board = modelMapper.map(boardRequestDto, Board.class);
            board.setCreateId(currentUser);

            LOGGER.info("[save] currentUser : {}", currentUser);

            // Save the board to the repository
            Board savedBoard = boardRepository.save(board);

            LOGGER.info("[save] savedBoard : {}", savedBoard);

            BoardResponseDto boardResponseDto = modelMapper.map(savedBoard, BoardResponseDto.class);
//            boardResponseDto.setCreateId(String.valueOf(currentUser));
            // Map the saved board to a response DTO and return it
            return boardResponseDto;
        } else {
            // Handle the case where the authentication or principal is not as expected
            LOGGER.error("[saveBoard] Authentication or principal is not as expected");
            throw new RuntimeException("Unable to retrieve the currently authenticated user's ID");
        }


//        LOGGER.info("[saveBoard] boardRequestDto : {}", boardRequestDto.toString());
//        Board board = modelMapper.map(boardRequestDto, Board.class);
//        board.setCreateId(userId);
//
//        Board savedBoard = boardRepository.save(board);
//        boardRepository.save(savedBoard);
//
//        LOGGER.info("[save] savedBoard : {}", savedBoard);
//
//        BoardResponseDto boardResponseDto = modelMapper.map(savedBoard, BoardResponseDto.class);
//
//        return boardResponseDto;
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
    public BoardResponseDto updateBoard(ChangeBoardDto changeBoardDto) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LOGGER.info("Authentication details: {}", authentication);

        if (authentication != null) {
            LOGGER.info("Principal class: {}", authentication.getPrincipal().getClass());
        }
        // Check if the authentication object is not null and the principal is a UserDetails instance
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Assuming your UserDetails implementation has a method to get the user ID
            String currentUserId = userDetails.getUsername();

            Board foundBoard = boardRepository.findById(changeBoardDto.getId()).orElse(null);

            LOGGER.info("[updateBoard] getCreateId : {}, currentUser : {}", foundBoard != null ? foundBoard.getCreateId() : "null", currentUserId);

            // 사용자 권한 확인
            if (foundBoard != null && currentUserId != null && foundBoard.getCreateId() != null &&
                    foundBoard.getCreateId().getUserId().equals(currentUserId)) {
                // 권한이 일치하면 게시물 업데이트
                foundBoard.setTitle(changeBoardDto.getTitle());
                foundBoard.setContents(changeBoardDto.getContents());
                foundBoard.setUseYn(changeBoardDto.getUseYn());
                foundBoard.setUpdatedAt(LocalDateTime.now());

                Board changedBoard = boardRepository.save(foundBoard);
                LOGGER.info("[updateBoard] changedBoard : {}", changedBoard);
                BoardResponseDto boardResponseDto = modelMapper.map(changedBoard, BoardResponseDto.class);
                return boardResponseDto;
            } else {
                // 권한이 일치하지 않으면 예외 처리 또는 적절한 로직 수행
                throw new Exception("You don't have permission to update this post.");
            }
        } else {
            // Handle the case where the authentication or principal is not as expected
            LOGGER.error("[saveBoard] Authentication or principal is not as expected");
            throw new RuntimeException("Unable to retrieve the currently authenticated user's ID");
        }


//
//        UserDetails currentUser = userDetailsService.loadByUsername(userId);  // Use the instance, not the class
//
//        Board foundBoard = boardRepository.findById(changeBoardDto.getId()).orElse(null);
//
//        LOGGER.info("[updateBoard] getCreateId : {}, currentUser : {}", foundBoard != null ? foundBoard.getCreateId() : "null", currentUser);
//
//        // 사용자 권한 확인
//        if (foundBoard != null && currentUser != null && foundBoard.getCreateId().equals(currentUser.getUsername())) {
//            // 권한이 일치하면 게시물 업데이트
//            foundBoard.setTitle(changeBoardDto.getTitle());
//            foundBoard.setContents(changeBoardDto.getContents());
//            foundBoard.setUseYn(changeBoardDto.getUseYn());
//            foundBoard.setUpdatedAt(LocalDateTime.now());
//
//            Board changedBoard = boardRepository.save(foundBoard);
//            LOGGER.info("[updateBoard] changedBoard : {}", changedBoard);
//            BoardResponseDto boardResponseDto = modelMapper.map(changedBoard, BoardResponseDto.class);
//            return boardResponseDto;
//        } else {
//            // 권한이 일치하지 않으면 예외 처리 또는 적절한 로직 수행
//            throw new Exception("You don't have permission to update this post.");
//        }


//        LOGGER.info("[updateBoard] boardRequestDto : {}", changeBoardDto.toString());
//        Board foundBoard = boardRepository.findById(changeBoardDto.getId()).get();
//
//        foundBoard.setTitle(changeBoardDto.getTitle());
//        foundBoard.setContents(changeBoardDto.getContents());
//        foundBoard.setUseYn(changeBoardDto.getUseYn());
//        foundBoard.setUpdatedAt(LocalDateTime.now());
//
//        Board changedBoard = boardRepository.save(foundBoard);
//
//        LOGGER.info("[updateBoard] changedBoard : {}", changedBoard);
//
//        BoardResponseDto boardResponseDto = modelMapper.map(changedBoard, BoardResponseDto.class);
//
//        return boardResponseDto;
    }

    @Override
    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }
}
