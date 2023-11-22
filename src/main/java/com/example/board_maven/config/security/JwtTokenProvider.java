package com.example.board_maven.config.security;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;

/**
 * JWT 토큰을 생성하고 유효성을 검증하는 컴포넌트 클래스 JWT 는 여러 암호화 알고리즘을 제공하고 알고리즘과 비밀키를 가지고 토큰을 생성
 * <p>
 * claim 정보에는 토큰에 부가적으로 정보를 추가할 수 있음 claim 정보에 회원을 구분할 수 있는 값을 세팅하였다가 토큰이 들어오면 해당 값으로 회원을 구분하여 리소스
 * 제공
 * <p>
 * JWT 토큰에 expire time을 설정할 수 있음
 */

// 예제 13.10
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${springboot.jwt.secret}")
    private String secretKey = "secretKey"; //토큰을 생성하기 위해서 secretKey가 필요
    private final long tokenValidMillisecond = 1000L * 30 * 60; // 30분 토큰 유효

    /**
     * SecretKey 에 대해 인코딩 수행
     * 예제 13.11
     */
    @PostConstruct //애플리케이션이 가동되면 자동으로 init() 실행
    protected void init() {
        LOGGER.info("[init] JwtTokenProvider 내 secretKey 초기화 시작");
        System.out.println(secretKey);
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8)); //secretKey를 Base64 형식으로 인코딩
        System.out.println(secretKey);
        LOGGER.info("[init] JwtTokenProvider 내 secretKey 초기화 완료");
    }

    // 예제 13.12
    // JWT 토큰 생성
    public String createToken(String userId) {
        LOGGER.info("[createToken] 토큰 생성 시작");
        Claims claims = Jwts.claims().setSubject(userId); //토큰의 내용에 값을 넣기 위한 Claims 객체

        Date now = new Date();
        String token = Jwts.builder() //토큰 생성
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidMillisecond))
                .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘, secret 값 세팅
                .compact();

        LOGGER.info("[createToken] 토큰 생성 완료");
        return token;
    }

    // 예제 13.13
    // JWT 토큰으로 인증 정보 조회
    public Authentication getAuthentication(String token) {
        LOGGER.info("[getAuthentication] 토큰 인증 정보 조회 시작");

        // 역할 정보 추출
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        String tokenUserId = (String) claims.get("userId");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = ((UserDetails) authentication.getPrincipal()).getUsername();


        if (tokenUserId.equals(currentUserId)) {
            LOGGER.info("[getAuthentication] 인증 정보를 확인하세요. UserDetails UserEmail: {}, Roles: {}", currentUserId, "My");

            // 역할 정보를 기반으로 권한 부여
            List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_" + "My");

            return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), "", authorities);
        } else {
            // Handle the case where the userId in the token does not match the currently logged-in user.
            // You can throw an exception or handle it as needed.
            LOGGER.warn("[getAuthentication] Token userId does not match the currently logged-in user.");
            return null; // Or handle it as needed in your application
        }
    }

    // 예제 13.14
    // JWT 토큰에서 회원 구별 정보 추출
    public String getUserId(String token) {
        LOGGER.info("[getUsername] 토큰 기반 회원 구별 정보 추출");
        String info = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        LOGGER.info("[getUsername] 토큰 기반 회원 구별 정보 추출 완료, info : {}", info);
        return info;
    }

    // 예제 13.15
    /**
     * HTTP Request Header 에 설정된 토큰 값을 가져옴
     *
     * @param request Http Request Header
     * @return String type Token 값
     */
    public String resolveToken(HttpServletRequest request) {
        LOGGER.info("[resolveToken] HTTP 헤더에서 Token 값 추출");
        return request.getHeader("X-AUTH-TOKEN");
    }

    // 예제 13.16
    // JWT 토큰의 유효성 + 만료일 체크
    public boolean validateToken(String token) {
        LOGGER.info("[validateToken] 토큰 유효 체크 시작");
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            LOGGER.info("[validateToken] 토큰 유효 체크 완료");
            return !claims.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            LOGGER.info("[validateToken] 토큰 만료");
            return false;
        } catch (JwtException e) {
            LOGGER.error("[validateToken] 토큰 유효 체크 예외 발생", e);
            return false;
        }
    }
}