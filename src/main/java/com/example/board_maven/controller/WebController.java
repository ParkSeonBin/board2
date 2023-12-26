package com.example.board_maven.controller;

import com.example.board_maven.data.dto.ListDto;
import com.example.board_maven.data.dto.UpdateDto;
import com.example.board_maven.data.entity.DetailDto;
import com.example.board_maven.service.impl.BoardServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class WebController {

    private final BoardServiceImpl boardService;
//
//    @GetMapping("/")
//    public String home() {
//        return "home";
//    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/signin")
    public String signin() {
        return "signin";
    }

    @GetMapping("/new")
    public String newPost() {
        return "new";
    }

    @GetMapping("/")
    public String index(Model model) {
        List<ListDto> posts = boardService.getAll();
        model.addAttribute("posts", posts);
        return "home";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model, @CookieValue("id") String memberId){
        DetailDto post = boardService.getDetail(id, memberId);
        model.addAttribute("post", post);
        model.addAttribute("now", memberId);

        return "detail";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model) {
        UpdateDto post = boardService.getUpdateDto(id);
        model.addAttribute("post", post);
        return "update";
    }
}
