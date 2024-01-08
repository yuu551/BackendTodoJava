package com.yjinno.todo.repository;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface TodoRepository {

    @Select("SELECT * FROM todos")
    List<TodoRecord> selectList();

    @Select("SELECT * FROM todos WHERE id = #{todoId}")
    Optional<TodoRecord> select(long todoId);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO todos (content,status,user_id,deadline) VALUES (#{content},#{status},#{userId},#{deadline})")
    void insert(TodoRecord record);

    @Update("UPDATE todos SET content = #{content},status = #{status},user_id = #{userId}, deadline = #{deadline} WHERE id = #{id}")
    void update(TodoRecord record);

    @Delete("DELETE FROM todos WHERE id = #{id}")
    void delete(long todoId);

}
