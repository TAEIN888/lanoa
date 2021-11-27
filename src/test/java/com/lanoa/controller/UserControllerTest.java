package com.lanoa.controller;

import com.lanoa.dto.UserFormDto;
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

import static org.junit.jupiter.api.Assertions.*;
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

    public User createUser(String email, String password) {
        UserFormDto userFormDto = new UserFormDto();
        userFormDto.setEmail(email);
        userFormDto.setUserName("홍길동");
        userFormDto.setAddress("서울시 서초구 방배동");
        userFormDto.setPassword(password);
        User user = User.createUser(userFormDto, passwordEncoder);
        return userService.saveUser(user);
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    public void loginSuccessTest() throws Exception {
        String email = "test@email.com";
        String password = "1234";
        this.createUser(email, password);

        mockMvc.perform(formLogin().userParameter("email")
                .loginProcessingUrl("/users/login")
                .user(email).password(password))
                .andExpect(SecurityMockMvcResultMatchers.authenticated());
    }

    @Test
    @DisplayName("로그인 실패 테스트")
    public void loginFailTest() throws Exception {
        String email = "test@email.com";
        String password = "1234";
        this.createUser(email, password);

        mockMvc.perform(formLogin().userParameter("email")
                        .loginProcessingUrl("/users/login")
                        .user(email).password("12345"))
                .andExpect(SecurityMockMvcResultMatchers.unauthenticated());
    }
}