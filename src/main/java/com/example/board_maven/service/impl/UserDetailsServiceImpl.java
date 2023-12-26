//package com.example.board_maven.service.impl;
//
//import com.example.board_maven.data.entity.BoardUser;
//import com.example.board_maven.data.repository.UserRepository;
//import com.example.board_maven.service.UserDetailsService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//
//@RequiredArgsConstructor
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    private final UserRepository userRepository;
//
//    @Override
//    public UserDetails loadByUsername(String userId) throws UsernameNotFoundException {
//        BoardUser user = userRepository.findByUserId(userId);
//
//        return new User(user.getUserId(), user.getPwd(), new ArrayList<>());
//    }
//}
