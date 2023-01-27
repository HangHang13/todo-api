package com.example.todo.userapi.repository;

import com.example.todo.userapi.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {



    //이메일로 회원조회
    UserEntity findByEmail(String email);

    //이메일 중복검사
    //select count(*) from tbl_user where email=?
//    @Query("select count(*) from UserEntity u where u.email=?1")
    boolean existsByEmail(String email);



}
