package com.example.todo.todoapi.service;


import com.example.todo.todoapi.dto.request.TodoCreateRequestDTO;
import com.example.todo.todoapi.dto.request.TodoModifyRequestDTO;
import com.example.todo.todoapi.dto.response.TodoDetailResponseDTO;
import com.example.todo.todoapi.dto.response.TodoListResponseDTO;
import com.example.todo.todoapi.entity.TodoEntity;
import com.example.todo.todoapi.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class TodoService {

    private final TodoRepository todoRepository;



//    @RequiredArgsConstructor 로 대체가능
//    @Autowired //스프링 4이상은 생성자 하나일때 자동 오토와이어드
//    public TodoService(TodoRepository todoRepository) {
//        this.todoRepository = todoRepository;}


    //할 일 목록 조회
    public TodoListResponseDTO retrieve(

            //인증완료처리시 등록했던 값을 넣어줌
            String userId){

        List<TodoEntity> entityList = todoRepository.findByUser(userId);
        List<TodoDetailResponseDTO> todoList = entityList.stream()
                .map(TodoDetailResponseDTO::new)
                .collect(Collectors.toList());

        return TodoListResponseDTO.builder()
                .todos(todoList)
                .build();
    }

    //할 일 등록
    public TodoListResponseDTO create(final TodoCreateRequestDTO createRequestDTO,
                                      String userId){
        TodoEntity todo = createRequestDTO.todoEntity();

        todo.setUserId(userId);
        todoRepository.save(todo);
        log.info("할 일이 저장되었습니다. 제목 : {}",createRequestDTO.getTitle());
        return retrieve(userId);
    }

    //할 일 수정(제목, 할일 완료 여부)
    public TodoListResponseDTO update(
            final String id, final TodoModifyRequestDTO todoModifyRequestDTO,
            final String userId){

        Optional<TodoEntity> targetEntity = todoRepository.findById(id);

        targetEntity.ifPresent(entity->{
            entity.setTitle(todoModifyRequestDTO.getTitle());
            entity.setDone(todoModifyRequestDTO.isDone());
            entity.setModifiedDate(LocalDateTime.now());
            todoRepository.save(entity);
        });

        return retrieve(userId);

    }

    //할 일 삭제
    public TodoListResponseDTO delete(final String id, String userId){
        try {
            todoRepository.deleteById(id);
        }catch (Exception e){
            log.error("id가 존재하지 않아 삭제에 실패했습니다. ID : {}, err : {} "
            ,id, e.getMessage());
            throw new RuntimeException("id가 존재하지 않아 삭제에 실패했습니다.");
        }
        return retrieve(userId);
    }
}
