package com.vibe.todo.repository;

import com.vibe.todo.entity.SubTodo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubTodoRepository extends JpaRepository<SubTodo, Long> {
}
