package com.example.todo.todoapi.controller;

import com.example.todo.todoapi.dto.request.TodoCreateRequestDTO;
import com.example.todo.todoapi.dto.request.TodoModifyRequestDTO;
import com.example.todo.todoapi.dto.response.TodoListResponseDTO;
import com.example.todo.todoapi.repository.TodoRepository;
import com.example.todo.todoapi.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:3000/")
//@CrossOrigin(origins = "*")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/todos")
public class TodoApiController {
    private final TodoService todoService;
    // 할 일 등록 요청
    @PostMapping
    public ResponseEntity<?> createTodo(@AuthenticationPrincipal String userId, @Validated @RequestBody TodoCreateRequestDTO todoCreateRequestDTO
            , BindingResult result){
        if (result.hasErrors()){
            log.warn("DTO 검증 에러 발생 : {} ", result.getFieldError());
            return ResponseEntity
                    .badRequest()
                    .body(result.getFieldError());
        }
        try {
            TodoListResponseDTO todoListResponseDTO = todoService.create(todoCreateRequestDTO, userId);

            return ResponseEntity
                    .ok()
                    .body(todoListResponseDTO);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity
                    .internalServerError()
                    .body(TodoListResponseDTO.builder().error(e.getMessage()).build());
        }
    }

    //할 일 삭제 요청
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTodo(@AuthenticationPrincipal String userId,@PathVariable("id") String todoId){

        log.info("/api/todos/{} DELETE request!", todoId);

        if(todoId == null || todoId.equals("")){
            return ResponseEntity
                    .badRequest()
                    .body(TodoListResponseDTO.builder().error("ID를 전달해 주세요"));
        }

        try {
            TodoListResponseDTO delete = todoService.delete(todoId, userId);
            return ResponseEntity
                    .ok()
                    .body(delete);
        }catch (Exception e){
            return ResponseEntity.internalServerError()
                    .body(TodoListResponseDTO.builder().error(e.getMessage()).build());
        }
    }

    //할 일 목록요청
    @GetMapping
    public ResponseEntity<?> getListAll(@AuthenticationPrincipal String userId){
        TodoListResponseDTO todos = todoService.retrieve(userId);
        if (todos.getTodos().size() == 0){
            return ResponseEntity.badRequest()
                    .body("할 일이 없습니다.");
        }else {
            return ResponseEntity.ok()
                    .body(todos);
        }
    }


    //할 일 수정요청(put, patch)
    @PatchMapping("/{todoId}")
    private ResponseEntity<?> update(@AuthenticationPrincipal String userId,@PathVariable("todoId") String todoId, @Validated @RequestBody TodoModifyRequestDTO modify){
//        TodoListResponseDTO update = todoService.update(todoId, modify);
//        return ResponseEntity.ok().body(update);
        try {
            TodoListResponseDTO update = todoService.update(todoId, modify,userId);
            return ResponseEntity.ok()
                    .body(update);
        }catch (RuntimeException e){
            return ResponseEntity
                    .internalServerError()
                    .body(e.getMessage());
        }
    }
}
