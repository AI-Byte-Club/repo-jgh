package com.vibe.todo.service;

import com.vibe.todo.entity.SubTodo;
import com.vibe.todo.entity.Todo;
import com.vibe.todo.repository.SubTodoRepository;
import com.vibe.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoService {

    private final TodoRepository todoRepository;
    private final SubTodoRepository subTodoRepository;

    public List<Todo> findAll() {
        return todoRepository.findAll();
    }

    @Transactional
    public Todo save(Todo todo) {
        return todoRepository.save(todo);
    }

    @Transactional
    public void delete(Long id) {
        todoRepository.deleteById(id);
    }

    @Transactional
    public Todo toggleComplete(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid todo Id:" + id));
        todo.setCompleted(!todo.isCompleted());
        return todo;
    }

    @Transactional
    public Todo updateTitle(Long id, String newTitle) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid todo Id:" + id));
        todo.setTitle(newTitle);
        return todo;
    }

    @Transactional
    public SubTodo addSubTodo(Long todoId, SubTodo subTodo) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid todo Id:" + todoId));
        subTodo.setTodo(todo);
        return subTodoRepository.save(subTodo);
    }

    @Transactional
    public void deleteSubTodo(Long id) {
        subTodoRepository.deleteById(id);
    }
}
