package com.example.todolist_backend.service;

import com.example.todolist_backend.domain.RefreshToken;
import com.example.todolist_backend.domain.User;
import com.example.todolist_backend.dto.user.UserLoginRequest;
import com.example.todolist_backend.dto.ResponseDto;
import com.example.todolist_backend.dto.user.UserJoinRequest;
import com.example.todolist_backend.dto.user.UserLoginData;
import com.example.todolist_backend.dto.user.UserLoginResponse;
import com.example.todolist_backend.exception.ExceptionMessage;
import com.example.todolist_backend.exception.UserAlreadyExistException;
import com.example.todolist_backend.repository.RefreshTokenRepository;
import com.example.todolist_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
    private final PasswordEncoder passwordEncoder; // config 로 @Bean 등록

    public void join(UserJoinRequest dto) {
        String account = dto.getAccount();
        String password = dto.getPassword(); // 비밀번호 체크는 프론트에서
        String email = dto.getEmail();
        // 🎄 서비스는 에러발생만 -> 에러처리는 컨트롤러에서

        // account 중복확인
        if(userRepository.existsByAccount((account))) {
//             throw new UserAlreadyExistException();
              throw new IllegalStateException(ExceptionMessage.ACCOUNT_DUPLICATED);
        }
        // 이메일 중복확인
        if(userRepository.existsByEmail(email)) {
            throw new IllegalStateException(ExceptionMessage.EMIAL_DUPLICATED);
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(password);
        // 유저 생성
         User user = User.builder()
                 .account(account)
                 .userName(dto.getUserName())
                 .email(dto.getEmail())
                 .password(encodedPassword)
                 .build();

        userRepository.save(user);
    }

    public ResponseDto<UserLoginResponse> login(UserLoginRequest dto) {
        String account = dto.getAccount(); // 🌈 spring validate 설정 추가하기??
        String password = dto.getPassword();

        User user = userRepository.findByAccount(account)
                .filter(it -> passwordEncoder.matches(password, it.getPassword())) // passwordEncoder.matches : matches 는 암호화된 문자열을 DB 등에 저장된 값과 비교할 때 사용
                  .orElseThrow(()-> new IllegalArgumentException("아이디 또는 비밀번호 일치하지 않습니다."));

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
