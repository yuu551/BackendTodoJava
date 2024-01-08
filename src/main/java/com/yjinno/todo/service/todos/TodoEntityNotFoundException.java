package com.yjinno.todo.service.todos;

public class TodoEntityNotFoundException extends RuntimeException {

    private long todoId;

    public TodoEntityNotFoundException(long todoId) {
        super("TaskEntity (id = " + todoId + ") is not found.");
        this.todoId = todoId;
    }
}
