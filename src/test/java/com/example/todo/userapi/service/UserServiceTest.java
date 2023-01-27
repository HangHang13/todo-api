package com.example.todo.userapi.service;

import com.example.todo.config.security.TokenProvider;
import com.example.todo.userapi.dto.UserLoginDTO;
import com.example.todo.userapi.dto.UserLoginResponseDTO;
import com.example.todo.userapi.dto.UserSignUpDTO;
import com.example.todo.userapi.dto.UserSignUpResponseDTO;
import com.example.todo.userapi.entity.UserEntity;
import com.example.todo.userapi.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class UserServiceTest {


    @Autowired
    UserService userService;

    @Autowired
    TokenProvider tokenProvider;
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("중복된 이메일이 포함된 회원정보로 가입하면 RuntimeException이 발생해야 한다.")
    void validateEmailTest(){
        //given
        UserSignUpDTO dto = UserSignUpDTO.builder()
                .email("abc1234@def.com")
                .password("1234")
                .userName("키키킥")
                .build();


        //when
        //then
        assertThrows(RuntimeException.class, ()->{
            userService.create(dto);
        });
    }

    @Test
    @DisplayName("검증된 회원정보로 가입하면 회원가입에 성공해야한다.")
    void createTest() {
        //given
        UserSignUpDTO dto = UserSignUpDTO.builder()
                .email("zv@def.com")
                .password("12345")
                .userName("키키")
                .build();


        //when
        UserSignUpResponseDTO responseDTO = userService.create(dto);
        //then
        System.out.println("responseDTO = " + responseDTO);
        assertEquals("키키", responseDTO.getUserName());

    }

    @Test
    @DisplayName("존재하지 않는 이메일로 로그인을 시도하면 Exception이 발생해야한다.")
    void noUserTest() {
        //given
        String email = "dsfdsff@aasad.com";
        String password = "1234";
        UserLoginDTO userLoginDTO = new UserLoginDTO(email,password);
        //then
        assertThrows(RuntimeException.class, () -> {

            //when
            userService.getByCredentials(userLoginDTO);
        });
    }

    @Test
    @DisplayName("틀린 비밀번호로 로그인을 시도하면 Exception이 발생해야한다.")
    void invalidatePasswordTest() {
        //given
        String email = "rlawlsgod@naver.com";
        String password = "1234";
        UserLoginDTO userLoginDTO = new UserLoginDTO(email,password);
        //then
        assertThrows(RuntimeException.class, () -> {

            //when
            userService.getByCredentials(userLoginDTO);
        });
    }

    @Test
    @DisplayName("정확한 정보로 로그인을 시도하면 회원정보가 반환 되야한다.")
    void loginTest() {
        //given
        String email = "rlawlsgod1@naver.com";
        String password = "rlawlsgod1!";
        UserLoginDTO userLoginDTO = new UserLoginDTO(email,password);

        //when

        UserLoginResponseDTO loginUser1 = userService.getByCredentials(userLoginDTO);

        //then
        assertEquals("우체부", loginUser1.getUserName());
    }
}