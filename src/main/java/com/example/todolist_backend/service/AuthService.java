package com.example.todolist_backend.service;

import com.example.todolist_backend.domain.RefreshToken;
import com.example.todolist_backend.domain.User;
import com.example.todolist_backend.dto.UserLoginRequest;
import com.example.todolist_backend.dto.ResponseDto;
import com.example.todolist_backend.dto.user.UserJoinRequest;
import com.example.todolist_backend.dto.user.UserLoginData;
import com.example.todolist_backend.dto.user.UserLoginResponse;
import com.example.todolist_backend.repository.RefreshTokenRepository;
import com.example.todolist_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
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

        User user = userRepository.findByAccount(account)
                .filter(it -> passwordEncoder.matches(password, it.getPassword())) // // passwordEncoder.matches : matches 는 암호화된 문자열을 DB 등에 저장된 값과 비교할 때 사용
                 .orElseThrow(()-> new IllegalArgumentException("아이디 또는 비밀번호 일치하지 않습니다."));
                // .orElseThrow(()-> new AuthenticationException("아이디 또는 비밀번호 일치하지 않습니다."));

        String token = tokenProvider.create(user.getId());
        String refreshToken = tokenProvider.createRefreshToken();
        int experTime = 1000 * 60 * 60;

        // 리프레시 토큰이 있다면 토큰 갱신, 없다면 리프레시토큰 생성 및 저장
        refreshTokenRepository.findById(user.getId())
                .ifPresentOrElse(it->it.updateRefreshToken(refreshToken), ()->refreshTokenRepository.save(new RefreshToken(user, refreshToken)));

        UserLoginData userData = new UserLoginData(user.getId(), user.getAccount(), user.getUserName());
        UserLoginResponse userLoginResponseDto = new UserLoginResponse(token, experTime, userData, refreshToken);

        return ResponseDto.setSuccess("로그인 성공했습니다.", userLoginResponseDto);
    }

    // 회원정보 조회 & 로그인 인증
    public ResponseDto<UserLoginData> getUserInfo(String bearerToken) {
        String accessToken;
        // accessToken 추출
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            accessToken = bearerToken.substring(7); // substring(n) : 인덱스가 n 이후인 값 반환
        } else {
            return null;
        }
        // 토큰 유효성검증 가져오기
        String userIdString=tokenProvider.validate(accessToken);
        Long userId = Long.parseLong(userIdString);

        User user = userRepository.findById(userId).orElse(null);
        if(user==null) {
            return ResponseDto.setFailed("해당 유저 정보가 없습니다.");
        }
        UserLoginData userData = new UserLoginData(user.getId(), user.getAccount(), user.getUserName());
        return ResponseDto.setSuccess("유저 정보조회에 성공했습니다.", userData);
    }

    //    @Transactional
    //    public Object reissue(TokenRequestDto tokenRequestDto) {
    //        // 1. Refresh Token 검증
    //        // 2. Access Token 에서 userId 가져오기
    //        // 3. 저장소에서 userId 를 기반으로 RefreshToken 가져옴
    //        // 4. Refresh Token dlfclgksmswl rjatk
    //        // 5. 새로운 토큰생성
    //        // 6. 저장소 정보 업데이트
    //        // 토큰 발급
    //    }

}
