package com.example.board_maven.service;

import com.example.board_maven.data.dto.SignInResultDto;
import com.example.board_maven.data.dto.SignUpResultDto;
import com.example.board_maven.data.dto.UserRequestDto;

public interface SignService {
    SignUpResultDto SignUp(UserRequestDto userRequestDto) throws Exception;
    SignInResultDto SignIn(String userId, String pwd) throws RuntimeException;
    void logout() throws Exception;
}
