package com.lanoa.controller;

import com.lanoa.constant.Role;
import com.lanoa.entity.User;
import com.lanoa.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class UserControllerTest {

    @Autowired
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("로그인 성공 테스트")
    public void loginSuccessTest() throws Exception {
        User savedUser = userService.saveUser(User.builder()
                .userName("홍길동")
                .email("test@email.com")
                .password("1234")
                .address("서울시 마포구 합정동")
                .role(Role.USER)
                .passwordEncoder(passwordEncoder)
                .build());

        mockMvc.perform(formLogin().userParameter("email")
                .loginProcessingUrl("/users/login")
                .user(savedUser.getEmail()).password("1234"));
    }

    @Test
    @DisplayName("로그인 실패 테스트")
    public void loginFailTest() throws Exception {
        User savedUser = userService.saveUser(User.builder()
                .userName("홍길동")
                .email("test@email.com")
                .password("1234")
                .address("서울시 마포구 합정동")
                .role(Role.USER)
                .passwordEncoder(passwordEncoder)
                .build());

        mockMvc.perform(formLogin().userParameter("email")
                        .loginProcessingUrl("/users/login")
                        .user(savedUser.getEmail()).password("12345"))
                .andExpect(SecurityMockMvcResultMatchers.unauthenticated());
    }
}