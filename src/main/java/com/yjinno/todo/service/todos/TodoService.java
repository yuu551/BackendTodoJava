package com.yjinno.todo.service.todos;

import com.yjinno.todo.repository.TodoRecord;
import com.yjinno.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    public List<TodoEntity> find() {
        return todoRepository.selectList().stream().map(record -> new TodoEntity(record.getId(), record.getContent(), record.getDeadline(), record.getUserId(), record.getStatus())).collect(Collectors.toList());
    }

    public TodoEntity find(long todoId) {
        return todoRepository.select(todoId).map(record -> new TodoEntity(record.getId(), record.getContent(), record.getDeadline(), record.getUserId(), record.getStatus())).orElseThrow(() -> new TodoEntityNotFoundException(todoId));

    }

    public TodoEntity create(String content, LocalDateTime deadline,long userId,String status){
        var record = new TodoRecord(null,content,status,userId,deadline);
        todoRepository.insert(record);
        return new TodoEntity(record.getId(), record.getContent(), record.getDeadline(), record.getUserId(), record.getStatus());
    }

    public TodoEntity update(long todoId,String content, LocalDateTime deadline,long userId,String status){
        todoRepository.select(todoId).orElseThrow(() -> new TodoEntityNotFoundException(todoId));
        todoRepository.update(new TodoRecord(todoId,content,status,userId,deadline));
        return find(todoId);
    }

    public void delete(long todoId){
        todoRepository.select(todoId).orElseThrow(() -> new TodoEntityNotFoundException(todoId));
        todoRepository.delete(todoId);
    }

}
