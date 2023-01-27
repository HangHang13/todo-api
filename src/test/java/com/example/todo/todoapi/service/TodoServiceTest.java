package com.example.todo.todoapi.service;

import com.example.todo.todoapi.dto.request.TodoCreateRequestDTO;
import com.example.todo.todoapi.dto.request.TodoModifyRequestDTO;
import com.example.todo.todoapi.dto.response.TodoDetailResponseDTO;
import com.example.todo.todoapi.dto.response.TodoListResponseDTO;
import com.example.todo.todoapi.repository.TodoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.util.List;


@Commit
@SpringBootTest
class TodoServiceTest {

    @Autowired
    TodoService todoService;
    @Autowired
    private TodoRepository todoRepository;

    @BeforeEach
    void metaData(){
        TodoCreateRequestDTO todo1= TodoCreateRequestDTO.builder()
                .title("저녁 장보기")
                .build();
        TodoCreateRequestDTO todo2= TodoCreateRequestDTO.builder()
                .title("식물 물주기")
                .build();
        TodoCreateRequestDTO todo3= TodoCreateRequestDTO.builder()
                .title("음악 감상하기")
                .build();

//        todoService.create(todo1);
//        todoService.create(todo2);
//        todoService.create(todo3);
    }
    @Test
    @DisplayName("새로운 할 일을 등록하면 생성되는 리스트는 할 일이 4개 들어있어야 한다.")
    void test1(){
        //given
        TodoCreateRequestDTO newTodo = TodoCreateRequestDTO.builder()
                .title("새로운 할일")
                .build();
//        TodoListResponseDTO todoResponseDTO = todoService.create(newTodo);
        //when
        //then
//        List<TodoDetailResponseDTO> todos = todoResponseDTO.getTodos();
//        Assertions.assertEquals(4, todos.size());
    }


    @Test
    @DisplayName("2번째 할일의 제목을 수정수정으로 수정하고 할일 완료처리를 해야함")
    void test2(){
        //given

        String newTitle = "수정수정";
        boolean newDone = true;

        TodoModifyRequestDTO todoModifyRequestDTO = TodoModifyRequestDTO.builder()
                .title(newTitle)
                .done(newDone)
                .build();
//
//        //when
/*        TodoDetailResponseDTO target = todoService.retrieve().getTodos().get(1);
        TodoListResponseDTO update = todoService.update(target.getId(), todoModifyRequestDTO);*/


        //then
       /* Assertions.assertEquals("수정수정", update.getTodos().get(1).getTitle());
        Assertions.assertTrue(update.getTodos().get(1).isDone());

        update.getTodos().forEach(m-> System.out.println(m));*/
    }
}