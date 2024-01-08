package com.yjinno.todo.service.todos;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class TodoEntity {

    long id;
    String content;
    LocalDateTime deadline;
    long userId;
    String status;
}
