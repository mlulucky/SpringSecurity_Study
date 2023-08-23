package com.example.todolist_backend.service;

import com.example.todolist_backend.domain.User;
import com.example.todolist_backend.dto.UserLoginRequest;
import com.example.todolist_backend.dto.ResponseDto;
import com.example.todolist_backend.dto.user.UserJoinRequest;
import com.example.todolist_backend.dto.user.UserLoginData;
import com.example.todolist_backend.dto.user.UserLoginResponse;
import com.example.todolist_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public ResponseDto<?> join(UserJoinRequest dto) {
        String account = dto.getAccount();
        String password = dto.getPassword();
        String passwordCheck = dto.getPasswordCheck(); // 비밀번호 체크

        // email 중복확인
        try { // repository 에 접근시에는 try - catch 예외처리하기! (데이터베이스에 접근불가한 경우 있음)
            if(userRepository.existsByAccount(account))
                return ResponseDto.setFailed("계정이 존재합니다.");
        } catch (Exception error) {
            return ResponseDto.setFailed("데이터베이스 에러");
        }

        // 비밀번호가 서로 다르면 failed response 반환
        if(!password.equals(passwordCheck))
            return ResponseDto.setFailed("비밀번호가 맞지 않습니다.");

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(password);
        // 유저 생성
         // User user = new User(dto);
         // user.setPassword(encodedPassword);
         User user = User.builder()
                 .account(account)
                 .userName(dto.getUserName())
                 .email(dto.getEmail())
                 .password(encodedPassword)
                 .build();

        // userRepository 이용해서 데이터베이스에 유저 저장
        try{
            userRepository.save(user);
        } catch (Exception error) {
            return ResponseDto.setFailed("데이터베이스 에러");
        }
        // 성공시 success response 반환
        return ResponseDto.setSuccess("회원가입 성공", null);
    }

    public ResponseDto<UserLoginResponse> login(UserLoginRequest dto) {
        String account = dto.getAccount(); // 🌈 spring validate 설정 추가하기??
        String password = dto.getPassword();
        User user = null;
        try {
            user = userRepository.findByAccount(account);
            // 잘못된 계정
            if(user == null) return ResponseDto.setFailed("로그인 실패 _ 잘못된 계정");
            // 잘못된 패스워드
            if(!passwordEncoder.matches(password, user.getPassword())) // passwordEncoder.matches : matches 는 암호화된 문자열을 DB 등에 저장된 값과 비교할 때 사용
                return ResponseDto.setFailed("로그인 실패 _ 잘못된 패스워드");
        } catch (Exception error) {
            return ResponseDto.setFailed("데이터베이스 에러");
        }

        user.setPassword("");  // 유저 비밀번호를 "" 으로 한다 -> 로그인 응답 데이터에서 비밀번호 안보이게 하기

        String token = tokenProvider.create(account);
        int experTime = 1000 * 60 * 60;

        UserLoginData userData = new UserLoginData(user.getId(), user.getAccount(), user.getUserName());

        UserLoginResponse userLoginResponseDto = new UserLoginResponse(token, experTime, userData);
        return ResponseDto.setSuccess("로그인 성공했습니다.", userLoginResponseDto);

    }
}
