package com.lanoa.service;

import com.lanoa.constant.Role;
import com.lanoa.dto.UserFormDto;
import com.lanoa.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원가입 테스트")
    public void saveUserTest() {

        User savedUser = userService.saveUser(User.builder()
                                        .userName("홍길동")
                                        .email("test@email.com")
                                        .password("1234")
                                        .address("서울시 마포구 합정동")
                                        .role(Role.USER)
                                        .passwordEncoder(passwordEncoder)
                                        .build());

        assertEquals("test@email.com", savedUser.getEmail());
        assertEquals("홍길동", savedUser.getUserName());
        assertEquals("서울시 마포구 합정동", savedUser.getAddress());
        assertEquals(Role.USER, savedUser.getRole());
    }

    @Test
    @DisplayName("중복 회원가입 불가 테스트")
    public void saveDuplicateUserTest() {
        User user1 = User.builder()
                .userName("홍길동")
                .email("test@email.com")
                .password("1234")
                .address("서울시 마포구 합정동")
                .role(Role.USER)
                .passwordEncoder(passwordEncoder)
                .build();

        User user2 = User.builder()
                .userName("홍길동")
                .email("test@email.com")
                .password("1234")
                .address("서울시 마포구 합정동")
                .role(Role.USER)
                .passwordEncoder(passwordEncoder)
                .build();

        userService.saveUser(user1);

        Throwable e = assertThrows(IllegalStateException.class, () -> {
           userService.saveUser(user2);
        });

        assertEquals("이미 등록된 사용자입니다.", e.getMessage());
    }

}