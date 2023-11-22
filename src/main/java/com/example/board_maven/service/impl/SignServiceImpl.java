package com.example.board_maven.service.impl;

import com.example.board_maven.common.CommonResponse;
import com.example.board_maven.config.security.JwtTokenProvider;
import com.example.board_maven.data.dto.SignInResultDto;
import com.example.board_maven.data.dto.SignUpResultDto;
import com.example.board_maven.data.dto.UserRequestDto;
import com.example.board_maven.data.entity.BoardUser;
import com.example.board_maven.data.repository.UserRepository;
import com.example.board_maven.service.SignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


// 예제 13.25
@Service
public class SignServiceImpl implements SignService {

    private final Logger LOGGER = LoggerFactory.getLogger(SignServiceImpl.class);

    public UserRepository userRepository;
    public JwtTokenProvider jwtTokenProvider;
    public PasswordEncoder passwordEncoder;

    @Autowired
    public SignServiceImpl(UserRepository userRepository, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public SignUpResultDto SignUp(UserRequestDto userRequestDto) throws Exception {
        LOGGER.info("[getSignUpResult] 회원 가입 정보 전달");
        BoardUser boardUser;
        boardUser = BoardUser.builder()
                .userId(userRequestDto.getUserId())
                .pwd(passwordEncoder.encode(userRequestDto.getPwd()))
                .build();

        boardUser.setCreatedAt(LocalDateTime.now());
        boardUser.setUpdatedAt(LocalDateTime.now());

        BoardUser savedUser = userRepository.save(boardUser);
        SignUpResultDto signUpResultDto = new SignInResultDto();

        LOGGER.info("[saveUser] BoardUser : {}", savedUser.toString());

        LOGGER.info("[getSignUpResult] userEntity 값이 들어왔는지 확인 후 결과값 주입");
        if (!savedUser.getUserId().isEmpty()) {
            LOGGER.info("[getSignUpResult] 정상 처리 완료");
            setSuccessResult(signUpResultDto);
        } else {
            LOGGER.info("[getSignUpResult] 실패 처리 완료");
            setFailResult(signUpResultDto);
        }

        return signUpResultDto;
    }

    @Override
    public SignInResultDto SignIn(String userId, String pwd){
        LOGGER.info("[getSignInResult] signDataHandler 로 회원 정보 요청");
        BoardUser boardUser = userRepository.findByUserId(userId);
        LOGGER.info("[getSignInResult] boardUser : {}", userId);

        LOGGER.info("[getSignInResult] 패스워드 비교 수행");
        if (!passwordEncoder.matches(pwd, boardUser.getPwd())) {
            LOGGER.info("[getSignInResult] 패스워드 불일치");
            throw new RuntimeException();
        }
        LOGGER.info("[getSignInResult] 패스워드 일치");

        LOGGER.info("[getSignInResult] SignInResultDto 객체 생성");
        SignInResultDto signInResultDto = SignInResultDto.builder()
                .token(jwtTokenProvider.createToken(boardUser.getUserId()))
                .build();

        LOGGER.info("[getSignInResult] SignInResultDto 객체에 값 주입");
        setSuccessResult(signInResultDto);

        return signInResultDto;
    }

    @Override
    public void logout() throws Exception {

    }

    private void setSuccessResult(SignUpResultDto result) {
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }

    // 결과 모델에 api 요청 실패 데이터를 세팅해주는 메소드
    private void setFailResult(SignUpResultDto result) {
        result.setSuccess(false);
        result.setCode(CommonResponse.FAIL.getCode());
        result.setMsg(CommonResponse.FAIL.getMsg());
    }
}
