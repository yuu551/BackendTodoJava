package com.yjinno.todo.repository;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class TodoRecord {

    Long id;
    String content;
    String status;
    long userId;
    LocalDateTime deadline;

}
