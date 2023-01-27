package com.example.todo.todoapi.repository;

import com.example.todo.todoapi.entity.TodoEntity;
import com.example.todo.userapi.entity.UserEntity;
import com.example.todo.userapi.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Commit // test 후 커밋
class TodoRepositoryTest {
    @Autowired
    TodoRepository todoRepository;
    @Autowired
    UserRepository userRepository;

    /*@BeforeEach
    void insertTest(){
        TodoEntity todos1 = TodoEntity.builder()
                .title("저녁 장보기")
                .build();
        TodoEntity todos2 = TodoEntity.builder()
                .title("치킨먹기")
                .build();
        TodoEntity todos3 = TodoEntity.builder()
                .title("코테 보기")
                .build();

        todoRepository.save(todos1);
        todoRepository.save(todos2);
        todoRepository.save(todos3);
    }*/
    @Test
    @DisplayName("회원의 할 일을 등록해야 한다.")
    void saveTodoWithUserTest(){
        UserEntity user1 = userRepository.findByEmail("rlawlsgod1@naver.com");
        //given
        TodoEntity todos1 = TodoEntity.builder()
                .title("국밥먹기")
                .user(user1)
                .build();
        //when
        TodoEntity saveTodo = todoRepository.save(todos1);
        //then
        assertEquals(saveTodo.getUser().getId(), user1.getId());
    }
    @Test
    @DisplayName("할 일 목록을 조회하면 리스트의 사이즈가 3이어야 한다.")
    void findAllTest(){
        //given
        //when
       List<TodoEntity> list = todoRepository.findAll();
        //then
        Assertions.assertEquals(3, list.size());

    }

    @Test
    @DisplayName("특정 회원의 할일 목록을 조회해야한다.")
    @Transactional
    void findByUserTest(){
        //given
        String userId = "402880c385f0b8c00185f0b915a30000";
        //when
        List<TodoEntity> todos = todoRepository.findByUser(userId);
        //then
        todos.forEach(System.out::println);
        Assertions.assertEquals(2, todos.size());

    }


}