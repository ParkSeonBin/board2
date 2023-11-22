package com.example.board_maven.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 어플리케이션의 보안 설정
 * 예제 13.19
 *
 * @author Flature
 * @version 1.0.0
 */
@Configuration
//@EnableWebSecurity // Spring Security에 대한 디버깅 모드를 사용하기 위한 어노테이션 (default : false)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public SecurityConfiguration(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.httpBasic().disable() // REST API는 UI를 사용하지 않으므로 기본설정을 비활성화
//
//                .csrf().disable() // REST API는 csrf 보안이 필요 없으므로 비활성화
//
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // JWT Token 인증방식으로 세션은 필요 없으므로 비활성화
//
//                .and()
//                .authorizeRequests() // 리퀘스트에 대한 사용권한 체크
//                .antMatchers("/login/**").permitAll() // 가입 및 로그인 주소는 허용
//                .antMatchers(HttpMethod.GET, "/board/**").permitAll()
//
//                .antMatchers("/board/**").hasRole("My")
//
//                .anyRequest().authenticated()
//
//                .and()
//                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class); // JWT Token 필터를 id/password 인증 필터 이전에 추가

        httpSecurity
                .httpBasic().disable() // Disable basic authentication
                .csrf().disable() // Disable CSRF protection
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Stateless session

                .and()
                .authorizeRequests()
                .antMatchers("/login/**").permitAll() // Allow login
                .antMatchers(HttpMethod.GET, "/board/**").permitAll() // Allow GET requests to board

                // Require authentication for these endpoints
                .antMatchers(HttpMethod.POST, "/board/save").authenticated()
                .antMatchers(HttpMethod.PUT, "/board/update").authenticated()
                .antMatchers(HttpMethod.DELETE, "/board/delete").authenticated()

                .and()
                .authorizeRequests()
                // Only allow users with the same ID as the creator to update or delete boards
                .antMatchers(HttpMethod.PUT, "/board/update", "/board/delete").access("hasRole('My') and @jwtTokenProvider.validateUserId(authentication, request)")

                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * Swagger 페이지 접근에 대한 예외 처리
     *
     * @param webSecurity
     */
    @Override
    public void configure(WebSecurity webSecurity) {
        webSecurity.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**",
                "/swagger-ui.html", "/webjars/**", "/swagger/**", "/login/exception");
    }
}