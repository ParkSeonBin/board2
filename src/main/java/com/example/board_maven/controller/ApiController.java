package com.example.board_maven.controller;

import com.example.board_maven.data.dto.BoardRequestDto;
import com.example.board_maven.data.dto.UpdateFormDto;
import com.example.board_maven.data.dto.UserRequestDto;
import com.example.board_maven.service.BoardService;
import com.example.board_maven.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    private final MemberService memberService;
    private final BoardService boardService;

//    @Autowired
//    public ApiController(MemberService memberService, BoardService boardService) {
//        this.memberService = memberService;
//        this.boardService = boardService;
//    }

    @PostMapping("/signup")
    public ResponseEntity userSignup(@RequestBody UserRequestDto formDTO) {
        return memberService.signup(formDTO);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserRequestDto loginDTO){
        return memberService.login(loginDTO);
    }

    @PostMapping("/posts")
    public ResponseEntity save(@RequestBody BoardRequestDto formDTO){
        return boardService.save(formDTO);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity remove(@PathVariable Long id) {
        ResponseEntity responseEntity = boardService.remove(id);
        return responseEntity;
    }

    @PatchMapping("/posts/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody UpdateFormDto updateFormDTO) {
        ResponseEntity responseEntity = boardService.update(id, updateFormDTO);

        return responseEntity;
    }
}