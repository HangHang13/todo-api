package com.example.todo.todoapi.repository;

import com.example.todo.todoapi.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, String> {


    //완료되지 않은 할 일들만 조회


    @Query("select t from TodoEntity t where t.done=1")
    List<TodoEntity> findNotYetTodos();

    @Query("select t from TodoEntity t where t.user.id=:userId")
    List<TodoEntity> findByUser(@Param(value = "userId") String user_id);
}
