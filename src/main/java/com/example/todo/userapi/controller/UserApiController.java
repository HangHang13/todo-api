package com.example.todo.userapi.controller;

import com.example.todo.userapi.dto.UserLoginDTO;
import com.example.todo.userapi.dto.UserLoginResponseDTO;
import com.example.todo.userapi.dto.UserSignUpDTO;
import com.example.todo.userapi.dto.UserSignUpResponseDTO;
import com.example.todo.userapi.exception.DuplicatedEmailException;
import com.example.todo.userapi.exception.NoRegisteredArgumentsException;
import com.example.todo.userapi.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserApiController {
    private final UserService userService;

    //회원가입 요청처리
    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@Validated @RequestBody UserSignUpDTO dto, BindingResult result){
        log.info("/api/auth/signUp POST - {}", dto);
        if (dto==null){
            throw new NoRegisteredArgumentsException("가입정보가 없습니다.");
        }
        if (result.hasErrors()){
            log.warn(result.toString());
            return ResponseEntity
                    .badRequest()
                    .body(result.toString());
        }

        try {
            UserSignUpResponseDTO resDTO = userService.create(dto);
            return ResponseEntity
                    .ok()
                    .body(resDTO);
        }catch (NoRegisteredArgumentsException e){
            //예외상황 2가지 dto가 null인 문제, 이메일 중복 문제
            log.warn(e.getMessage());
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }catch (DuplicatedEmailException e){
            log.warn(e.getMessage());
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
    //이메일 중복확인 요청 함수


    //이메일 중복확인 요청처리
    @GetMapping("/check")
    public ResponseEntity<?> checkEmail(@RequestParam(required = true) String email){ /*@RequestParam 은 안쓰면 생략되어있음, required가 있어서 안쓰는 경우
    스프링 단에서 까버림*/
        if (email==null || email.trim().equals("")){
            return ResponseEntity.badRequest().body("이메일을 입력해주세요");
        }
        boolean duplicate = userService.isDuplicate(email);
        log.info("{} 중복 여부 -- {} ",email,duplicate);
        return ResponseEntity.ok().body(duplicate);
    }

    @PostMapping("/signin")
        public ResponseEntity<?> signIn(@Validated @RequestBody UserLoginDTO userLoginDTO, BindingResult result){

        if (result.hasErrors()){
            log.warn("DTO 검증 에러 발생 : {} ", result.getFieldError());
            return ResponseEntity
                    .badRequest()
                    .body(result.getFieldError());
        }
        try {
            UserLoginResponseDTO userInfo = userService.getByCredentials(userLoginDTO);
            return ResponseEntity
                    .ok()
                    .body(userInfo);
        }catch (RuntimeException e){
            return ResponseEntity
                    .badRequest()
                    .body(UserLoginResponseDTO.builder().message(e.getMessage()).build());
        }
    }


}
