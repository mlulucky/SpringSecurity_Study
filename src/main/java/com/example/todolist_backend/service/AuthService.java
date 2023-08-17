package com.example.todolist_backend.service;

import com.example.todolist_backend.domain.User;
import com.example.todolist_backend.dto.user.ResponseDto;
import com.example.todolist_backend.dto.user.UserJoinRequest;
import com.example.todolist_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;

    public ResponseDto<?> join(UserJoinRequest dto) {
        String account = dto.getAccount();
        String password = dto.getPassword();
        String passwordCheck = dto.getPasswordCheck(); // 비밀번호 체크

        // email 중복확인
        try { // repository 에 접근시에는 try - catch 예외처리하기! (데이터베이스에 접근불가한 경우 있음)
            if(userRepository.existsByAccount(account))
                return ResponseDto.setFailed("계정이 존재합니다.");
        }catch (Exception e) {
            return ResponseDto.setFailed("데이터베이스 에러");
        }

        // 비밀번호가 서로 다르면 failed response 반환
        if(!password.equals(passwordCheck))
            return ResponseDto.setFailed("비밀번호가 맞지 않습니다.");

        // 유저 생성
        User user = new User(dto);

        // userRepository 이용해서 데이터베이스에 유저 저장
        try{
            userRepository.save(user);
        }catch (Exception e) {
            return ResponseDto.setFailed("데이터베이스 에러");
        }

        // 성공시 success response 반환
        return ResponseDto.setSuccess("회원가입 성공", null);
    }
}
