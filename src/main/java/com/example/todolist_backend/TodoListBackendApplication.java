package com.example.todolist_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

// @SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
// 당장 DB를 사용하지 않고 스프링 부트를 쓸 경우, exclude로 DataSource 클래스 호출을 제외시킨다.
@ComponentScan(basePackages = {"com.example.todolist_backend.repository"}) // 찾지 못하는 bean이 존재하는 package를 scan하도록 한다.
@SpringBootApplication
public class TodoListBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(TodoListBackendApplication.class, args);

	}

}
