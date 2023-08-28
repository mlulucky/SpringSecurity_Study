package com.example.todolist_backend.service;

import com.example.todolist_backend.domain.User;
import com.example.todolist_backend.dto.user.UserJoinRequest;
import com.example.todolist_backend.exception.AppException;
import com.example.todolist_backend.exception.ErrorCode;
import com.example.todolist_backend.repository.UserRepository;
import com.example.todolist_backend.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    @Value("${jwt.token.secret}") // application 환경변수로 지정
    private String key; // jwt 토큰생성 키
    private Long expireTimeMs = 1000 * 60 * 60l; // l : long

    public String join(UserJoinRequest dto) {

        // 🌈 userName 중복 체크 -> db 에서 확인 필요 -> Repository 필요 // findByUserName 는 기본 메서드가 아니라서 Repository 에 메서드 만들기
        userRepository.findByUserName(dto.getUserName())
                //.ifPresent(user -> { throw new RuntimeException(dto.getUserName() + "는 이미 있습니다.");
                .ifPresent(user -> { throw new AppException(ErrorCode.USERNAME_DUPLICATED, dto.getUserName() + "는 이미 있습니다.");
                }); // 유저가 있으면 -> 에러처리

        // 저장
        User user = User.builder()
                .account(dto.getAccount())
                .userName(dto.getUserName())
                .email(dto.getEmail())
                .password(encoder.encode(dto.getPassword())) // 🌈 encoder.encode() : 비밀번호 인코딩
                .build();

        userRepository.save(user);
        return "SUCCESS";
    }

//    public String login(String account, String password) {
//        // 로그인 실패1 - userName 없음
//        User selectedUser = userRepository.findByAccount(account)
//                .orElseThrow(()-> new AppException(ErrorCode.USERNAME_NOT_FOUND, account + "이 없습니다."));
//
//        // 로그인 실패2 - password 없음
//        // 인코딩 문자열을 비교하는 법(인코딩할때마다 매번 결과 달라짐) -> BCryptPasswordEncoder 클래스의 matches 메서드
//        log.info("selectedPw: {}, inputPw: {}", selectedUser.getPassword(), password);
//        if(!encoder.matches(password, selectedUser.getPassword())) {
//        // if(!encoder.matches(selectedUser.getPassword(), password)) {
//            throw new AppException(ErrorCode.INVALID_PASSWORD, "패스워드를 잘못 입력했습니다.");
//        }
//
//        // 앞에서 Exception 에러 안났으면 토큰 발행
//        String token = JwtTokenUtil.createToken(selectedUser.getAccount(), key, expireTimeMs);
//
//        // 로그인 성공 - token 리턴
//        return token;
//    }
}
