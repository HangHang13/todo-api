package com.example.todo.todoapi.entity;

// 일정관리 프로그램

import com.example.todo.userapi.entity.UserEntity;
import lombok.*;
import org.apache.catalina.User;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "todoId")
@Builder

@Entity
@Table(name = "tbl_todo")
public class TodoEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String todoId;

    @Column(nullable = false, length = 30)
    private String title; // 제목



    private boolean done; // 일정 완료 여부 정수형 기본값 = 0 실수 기본값 0.0, 논리 기본값 false

    @CreationTimestamp
    private LocalDateTime createDate; // 등록 시간

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    //회원과 관계설정
    @ManyToOne(fetch = FetchType.LAZY)
    //연관관계 설정은 했지만, INSERT, UPDATE시에는 이 객체를 활용하지 않겠다. 효과성능을위해서
    //기존에는 회원정보를 조회하는 쿼리를 날리고 조회한 객체를 setter에 넣었다. 이는 조회를 한번하고 수정을 날린다 => 성능문제
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserEntity user;

    //할일추가, 수정시 사용할 외래키
    @Column(name="user_id")
    private String userId;
}