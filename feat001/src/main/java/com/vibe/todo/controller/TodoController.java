package com.vibe.todo.controller;

import com.vibe.todo.entity.SubTodo;
import com.vibe.todo.entity.Todo;
import com.vibe.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // 프론트엔드 연동을 위해 허용
public class TodoController {

    private final TodoService todoService;

    @GetMapping
    public List<Todo> getAllTodos() {
        return todoService.findAll();
    }

    @PostMapping
    public Todo createTodo(@RequestBody Todo todo) {
        return todoService.save(todo);
    }

    @PatchMapping("/{id}")
    public Todo toggleTodo(@PathVariable Long id) {
        return todoService.toggleComplete(id);
    }

    @PutMapping("/{id}")
    public Todo updateTodo(@PathVariable Long id, @RequestBody Todo todo) {
        return todoService.updateTitle(id, todo.getTitle());
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable Long id) {
        todoService.delete(id);
    }

    @PostMapping("/{id}/sub")
    public SubTodo createSubTodo(@PathVariable Long id, @RequestBody SubTodo subTodo) {
        return todoService.addSubTodo(id, subTodo);
    }

    @DeleteMapping("/sub/{id}")
    public void deleteSubTodo(@PathVariable Long id) {
        todoService.deleteSubTodo(id);
    }
}
