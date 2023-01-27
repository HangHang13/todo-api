package com.example.todo.todoapi.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class TodoListResponseDTO {



    private String error;
    private List<TodoDetailResponseDTO> todos;




}
