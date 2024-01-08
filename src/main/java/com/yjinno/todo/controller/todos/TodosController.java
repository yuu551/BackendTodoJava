package com.yjinno.todo.controller.todos;

import com.yjinno.todo.controller.TodosApi;
import com.yjinno.todo.model.CreateTodoDto;
import com.yjinno.todo.model.GetTodosDto;
import com.yjinno.todo.model.TodoDto;
import com.yjinno.todo.service.todos.TodoEntity;
import com.yjinno.todo.service.todos.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class TodosController implements TodosApi {

    private final TodoService todoService;

    @Override
    public ResponseEntity<GetTodosDto> listTodos() {
        var entityList = todoService.find();
        var dtoList = entityList.stream()
                .map(this::toTodoDTO)
                .collect(Collectors.toList());

        var result = new GetTodosDto();
        result.setTodos(dtoList);

        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<TodoDto> getTodo(Long todoId) {
        var entity = todoService.find(todoId);
        var dto = toTodoDTO(entity);

        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<TodoDto> createTodo(CreateTodoDto form){
        var entity = todoService.create(form.getContent(),form.getDeadline().toLocalDateTime(), form.getUserId(), form.getStatus().getValue(), form.getCategory());
        var dto = toTodoDTO(entity);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<TodoDto> editTodo(Long todoId,TodoDto form){
        var entity = todoService.update(todoId,form.getContent(),form.getDeadline().toLocalDateTime(),form.getUserId(),form.getStatus().getValue(), form.getCategory());
        var dto = toTodoDTO(entity);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<Void> deleteTodo(Long todoId){
        todoService.delete(todoId);
        return ResponseEntity.noContent().build();
    }

    private TodoDto toTodoDTO(TodoEntity todoEntity) {
        var todoDTO = new TodoDto();
        todoDTO.setId(todoEntity.getId());
        todoDTO.setContent(todoEntity.getContent());
        todoDTO.setStatus(TodoDto.StatusEnum.fromValue(todoEntity.getStatus()));
        todoDTO.setDeadline(todoEntity.getDeadline().atOffset(ZoneOffset.ofHours(0)));
        todoDTO.setUserId(todoEntity.getUserId());
        todoDTO.setCategory(todoEntity.getCategory());
        return todoDTO;
    }

}
