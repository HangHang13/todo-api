package com.example.todo.todoapi.dto.request;


import com.example.todo.todoapi.entity.TodoEntity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoCreateRequestDTO {

    @NotBlank
    @Size(min = 2, max = 10)
    private String title; // 제목

    // 이 dto를 엔티티로 변환
    public TodoEntity todoEntity(){
        return TodoEntity.builder()
                .title(this.title)
                .build();
    }
}
