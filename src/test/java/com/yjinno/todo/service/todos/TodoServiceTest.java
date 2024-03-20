package com.yjinno.todo.service.todos;

import com.yjinno.todo.repository.TodoRecord;
import com.yjinno.todo.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TodoServiceTest {

    @InjectMocks
    TodoService todoService;

    @Mock
    TodoRepository todoRepository;

    @Test
    void test_findById(){
        TodoRecord record = new TodoRecord(1L,"test","completed",1, LocalDateTime.now(),"test");
        doReturn(Optional.of(record)).when(todoRepository).select(1);

        TodoEntity entity = todoService.find(1);
        assertThat(entity.getId()).isEqualTo(1);
    }

    @Test
    void test_create(){
        // TodoRecordのモック
        TodoRecord mockedRecord = mock(TodoRecord.class);
        when(mockedRecord.getId()).thenReturn(1L); // この行を追加

        when(mockedRecord.getContent()).thenReturn("test");
        when(mockedRecord.getDeadline()).thenReturn(LocalDateTime.now());
        when(mockedRecord.getUserId()).thenReturn(1L);
        when(mockedRecord.getStatus()).thenReturn("completed");
        when(mockedRecord.getCategory()).thenReturn("test");

        // todoRepository.insert(record)の振る舞いをスタブ
        doAnswer(invocation -> {
            TodoRecord argument = invocation.getArgument(0);
            return null;
        }).when(todoRepository).insert(any(TodoRecord.class));

        // create()メソッドの呼び出し
        TodoEntity entity = todoService.create(mockedRecord);

        // 検証
        assertThat(entity.getId()).isEqualTo(1L);
        assertThat(entity.getContent()).isEqualTo("test");
    }

}
