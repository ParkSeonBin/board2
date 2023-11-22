package com.example.board_maven.controller;

import com.example.board_maven.data.dto.SignInResultDto;
import com.example.board_maven.data.dto.SignUpResultDto;
import com.example.board_maven.data.dto.UserRequestDto;
import com.example.board_maven.service.SignService;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class SignController {

    private final Logger LOGGER = LoggerFactory.getLogger(SignController.class);
    private final SignService signService;

    @Autowired
    public SignController(SignService signService) {this.signService = signService;}


    @PostMapping(value = "/sign-in")
    public SignInResultDto signIn(@ApiParam(value = "ID", required = true) @RequestParam String id,
                                  @ApiParam(value = "Password", required = true) @RequestParam String pwd) throws RuntimeException {
        LOGGER.info("[signIn] 로그인을 시도하고 있습니다. id : {}, pw : ****", id);
        SignInResultDto signInResultDto = signService.SignIn(id, pwd);

        if (signInResultDto.getCode() == 0) {
            LOGGER.info("[signIn] 정상적으로 로그인되었습니다. id : {}, token : {}", id,
                    signInResultDto.getToken());
        }
        return signInResultDto;
    }

    @PostMapping(value = "/sign-up")
    public ResponseEntity<SignUpResultDto> signUp(@RequestBody @Valid UserRequestDto userRequestDto) throws Exception {
        LOGGER.info("[signUp] 회원가입을 수행합니다. id : {}, password : ****", userRequestDto.getUserId());
        SignUpResultDto signUpResultDto = signService.SignUp(userRequestDto);

        LOGGER.info("[signUp] 회원가입을 완료했습니다. id : {}, pwd : {}", userRequestDto.getUserId(), userRequestDto.getPwd());
        return ResponseEntity.status(HttpStatus.OK).body(signUpResultDto);
    }

    @GetMapping(value = "/exception")
    public void exceptionTest() throws RuntimeException {
        throw new RuntimeException("접근이 금지되었습니다.");
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Map<String, String>> ExceptionHandler(RuntimeException e) {
//        HttpHeaders responseHeaders = new HttpHeaders();
//        //responseHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
//        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
//
//        LOGGER.error("ExceptionHandler 호출, {}, {}", e.getCause(), e.getMessage());
//
//        Map<String, String> map = new HashMap<>();
//        map.put("error type", httpStatus.getReasonPhrase());
//        map.put("code", "400");
//        map.put("message", "에러 발생");
//
//        return new ResponseEntity<>(map, responseHeaders, httpStatus);

        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        LOGGER.error("ExceptionHandler caught an exception", e);

        Map<String, String> map = new HashMap<>();
        map.put("error type", e.getClass().getSimpleName());
        map.put("message", e.getMessage());

        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<String> logout(HttpServletRequest servletRequest) throws Exception {
        signService.logout();

        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 로그아웃되었습니다.");
    }
}