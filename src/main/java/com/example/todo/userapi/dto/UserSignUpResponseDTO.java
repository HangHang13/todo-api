package com.example.todo.userapi.dto;


import com.example.todo.userapi.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.Email;
import java.time.LocalDateTime;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="email")
@Builder
public class UserSignUpResponseDTO {

    @Email
    private String email;


    private String userName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime joinDate;

    //엔티티를 dto로 변경하는 생성자

    public UserSignUpResponseDTO(UserEntity userEntity){
        this.email = userEntity.getEmail();
        this.userName = userEntity.getUserName();
        this.joinDate = userEntity.getJoinDate();
    }
}
