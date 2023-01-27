package com.example.todo.todoapi.dto.response;


import com.example.todo.todoapi.entity.TodoEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TodoDetailResponseDTO {


    private String id;
    private String title;

    private boolean done;

    @JsonFormat(pattern = "yyyy년 MM월 dd일 a hh시 mm분 ss초") //a는 오전 오후 표기
    private LocalDateTime regDate;

    @JsonFormat(pattern = "yyyy년 MM월 dd일 a hh시 mm분 ss초") //a는 오전 오후 표기
    private LocalDateTime modifiedDate;


    public TodoDetailResponseDTO(TodoEntity todo) {
        this.id = todo.getTodoId();
        this.title = todo.getTitle();
        this.done = todo.isDone();
        this.regDate = todo.getCreateDate();
        this.modifiedDate = todo.getModifiedDate();
    }


}
