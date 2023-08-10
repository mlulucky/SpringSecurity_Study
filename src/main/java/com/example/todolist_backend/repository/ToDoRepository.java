package com.example.todolist_backend.repository;

import com.example.todolist_backend.domain.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoRepository extends JpaRepository<ToDo, Long> {

}
