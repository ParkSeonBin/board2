package com.example.board_maven.service.impl;

import com.example.board_maven.data.dto.UserRequestDto;
import com.example.board_maven.data.entity.BoardUser;
import com.example.board_maven.data.entity.Role;
import com.example.board_maven.data.repository.MemberRepository;
import com.example.board_maven.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final Logger LOGGER = LoggerFactory.getLogger(BoardServiceImpl.class);
    private final MemberRepository memberRepository;

    public ResponseEntity signup(UserRequestDto formDTO) {

        Optional<BoardUser> member = memberRepository.findByUserId(formDTO.getUserId());

        if (member.isEmpty()) {
            BoardUser newMember = BoardUser.builder()
                    .userId(formDTO.getUserId())
                    .pwd(formDTO.getPwd())
                    .role(Role.USER)
                    .build();

            memberRepository.save(newMember);

            return new ResponseEntity("success", HttpStatus.OK);
        } else {
            return new ResponseEntity("fail", HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity login(UserRequestDto loginDTO) {
        Optional<BoardUser> member = memberRepository.findByUserId(loginDTO.getUserId());
        BoardUser memberEntity = member.orElse(null);

        if (member==null){
            LOGGER.info("해당 아이디를 가진 회원이 존재하지 않습니다.");
            return new ResponseEntity("해당 아이디를 가진 회원이 존재하지 않습니다.", HttpStatus.OK);
        }

        if (memberEntity.getPwd().equals(loginDTO.getPwd())){
            LOGGER.info("success");
            return new ResponseEntity("success", HttpStatus.OK);
        } else {
            LOGGER.info("비밀번호가 일치하지 않습니다.");
            return new ResponseEntity("비밀번호가 일치하지 않습니다.", HttpStatus.OK);
        }
    }
}