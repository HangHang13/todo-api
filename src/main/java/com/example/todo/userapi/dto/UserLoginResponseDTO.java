package com.example.todo.userapi.dto;


import com.example.todo.userapi.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="email")
@Builder
public class UserLoginResponseDTO {

    private String email;

    private String userName;


    @JsonFormat(pattern = "yyyy년 MM월 dd일")
    private LocalDate joinDate;

    //인증토큰
    private String token;

    private String message; //응답메세지

    //엔티티를 DTO로 변경

    public UserLoginResponseDTO(UserEntity userEntity, String token){
        this.email = userEntity.getEmail();
        this.userName = userEntity.getUserName();
        this.joinDate = LocalDate.from(userEntity.getJoinDate());
        this.token = token;
    }

}
