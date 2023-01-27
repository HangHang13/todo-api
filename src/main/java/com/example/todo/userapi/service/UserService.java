package com.example.todo.userapi.service;


import com.example.todo.config.security.TokenProvider;
import com.example.todo.userapi.dto.UserLoginDTO;
import com.example.todo.userapi.dto.UserLoginResponseDTO;
import com.example.todo.userapi.dto.UserSignUpDTO;
import com.example.todo.userapi.dto.UserSignUpResponseDTO;
import com.example.todo.userapi.entity.UserEntity;
import com.example.todo.userapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final TokenProvider tokenProvider;
    //회원 가입 처리
    public UserSignUpResponseDTO create(final UserSignUpDTO userSignUpDTO){
        if(userSignUpDTO == null){
            throw new RuntimeException("유효하지 않은 가입정보입니다.");
        }

        final String email = userSignUpDTO.getEmail();
        if (userRepository.existsByEmail(email)){
            log.warn("email already exist - {}",email);
            throw new RuntimeException("중복된 이메일입니다.");
        }
        //패스워드 인코딩
        String rawPassword = userSignUpDTO.getPassword();
        String encodePassword = passwordEncoder.encode(rawPassword);
        userSignUpDTO.setPassword(encodePassword);
        UserEntity savedUser = userRepository.save(userSignUpDTO.toEntity());

        log.info("회원 가입 성공!!");

        return new UserSignUpResponseDTO(savedUser);
    }
    //이메일 중복확인
    public boolean isDuplicate(String email){
        if(email == null){
            throw new RuntimeException("이메일 값이 없습니다.");
        }
        return userRepository.existsByEmail(email);
    }

    //로그인 검증
    public UserLoginResponseDTO getByCredentials(final UserLoginDTO userLoginDTO){
        String email = userLoginDTO.getEmail();
        String password = userLoginDTO.getPassword();

        //입력한 이메일을 통해 회원정보 조회
        UserEntity orginalUser = userRepository.findByEmail(email);

        if (orginalUser == null){
            throw new RuntimeException("가입된 회원이 아닙니다.");
        }

        //패스워드 검증
        if (!passwordEncoder.matches(password, orginalUser.getPassword())){
            throw new RuntimeException("비밀번호가 틀렸습니다.");
        }

        log.info("{} 님 로그인 성공", orginalUser.getUserName());
        //토큰발급
        String token = tokenProvider.createToken(orginalUser);
        return new UserLoginResponseDTO(orginalUser,token);
    }
}
