package com.example.todolist_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(staticName = "set") // staticName = "set"을 사용함으로써 해당 클래스의 인스턴스를 생성할 때 생성자 대신 set 이라는 이름의 정적 메서드를 호출하여 객체를 생성할 수 있다.
@Getter
public class ResponseDto<T> { // data 타입이 뭐가 올지 몰라서 제네릭 T
    public boolean result;
    public String message;
    public T data;

    public static <T> ResponseDto<T> setSuccess(String message, T data) { // static <T> 의 <T> 제네릭을 써야 메서드 내에서 <T> 제네릭 쓸 수 있다.
        return ResponseDto.set(true, message, data); // == new ResponseDto<T>(true, message, data)
    }

    public static <T> ResponseDto<T> setFailed(String message) {
        return new ResponseDto<T>(false, message, null);
    }
}
