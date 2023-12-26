//package com.example.board_maven.config.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
///**
// * 어플리케이션의 보안 설정
// * 예제 13.19
// *
// * @author Flature
// * @version 1.0.0
// */
//@Configuration
////@EnableWebSecurity // Spring Security에 대한 디버깅 모드를 사용하기 위한 어노테이션 (default : false)
//public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//    private final JwtTokenProvider jwtTokenProvider;
//
//    @Autowired
//    public SecurityConfiguration(JwtTokenProvider jwtTokenProvider) {
//        this.jwtTokenProvider = jwtTokenProvider;
//    }
//
//    @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//                .httpBasic().disable() // Disable basic authentication
//                .csrf().disable() // Disable CSRF protection
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Stateless session
//
//                .and()
//                .authorizeRequests() // 리퀘스트에 대한 사용권한 체크
//                .antMatchers("/login/**").permitAll() // Allow login
//                .antMatchers("/board/**").permitAll() // Allow GET requests to board
//                .antMatchers("/main/**").permitAll()
//
//                .antMatchers("**exception**").permitAll()
//
////                .anyRequest().hasRole("ADMIN") // 나머지 요청은 인증된 ADMIN만 접근 가능
////
//                .and()
//                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
//    }
//
//    /**
//     * Swagger 페이지 접근에 대한 예외 처리
//     *
//     * @param webSecurity
//     */
//    @Override
//    public void configure(WebSecurity webSecurity) {
//        webSecurity.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**",
//                "/swagger-ui.html", "/webjars/**", "/swagger/**", "/login/exception");
//    }
//}