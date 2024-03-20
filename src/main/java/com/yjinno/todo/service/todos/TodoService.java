package com.yjinno.todo.service.todos;

import com.yjinno.todo.repository.TodoRecord;
import com.yjinno.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    public List<TodoEntity> find() {
        return todoRepository.selectList().stream().map(record -> new TodoEntity(record.getId(), record.getContent(), record.getDeadline(), record.getUserId(), record.getStatus(),record.getCategory())).collect(Collectors.toList());
    }

    public TodoEntity find(long todoId) {
        return todoRepository.select(todoId).map(record -> new TodoEntity(record.getId(), record.getContent(), record.getDeadline(), record.getUserId(), record.getStatus(), record.getCategory())).orElseThrow(() -> new TodoEntityNotFoundException(todoId));

    }

    public TodoEntity create(TodoRecord record){
        todoRepository.insert(record);
        return new TodoEntity(record.getId(), record.getContent(), record.getDeadline(), record.getUserId(), record.getStatus(), record.getCategory());
    }

    public TodoEntity update(long todoId,String content, LocalDateTime deadline,long userId,String status,String category){
        todoRepository.select(todoId).orElseThrow(() -> new TodoEntityNotFoundException(todoId));
        todoRepository.update(new TodoRecord(todoId,content,status,userId,deadline,category));
        return find(todoId);
    }

    public void delete(long todoId){
        todoRepository.select(todoId).orElseThrow(() -> new TodoEntityNotFoundException(todoId));
        todoRepository.delete(todoId);
    }

}
