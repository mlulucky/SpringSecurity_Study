package com.example.todolist_backend.service;

import com.example.todolist_backend.repository.UserRepsoitory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
//@RequiredArgsConstructor
//@Service
// public class UserDetailService implements UserDetailsService {
//    private final UserRepsoitory userRepsoitory;
//
//    public UserDetailService(UserRepsoitory userRepsoitory) {
//        this.userRepsoitory = userRepsoitory;
//    }
//
//    // 사용자 email 로 사용자 정보를 가져오는 메서드
//    @Override
//    public UserDetails loadUserByUsername(String email) {
//        return userRepsoitory.findByEmail(email)
//                .orElseThrow(() -> new IllegalArgumentException(email));
//    }
// }
