package com.lanoa.controller;

import com.lanoa.constant.Role;
import com.lanoa.dto.UserFormDto;
import com.lanoa.entity.User;
import com.lanoa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/users")
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping(value = "new")
    public String userForm(Model model) {
        model.addAttribute("userFormDto", new UserFormDto());

        return "user/userForm";
    }

    @PostMapping(value = "/new")
    public String userForm(@Valid UserFormDto userFormDto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "user/userForm";
        }

        try {
            userService.saveUser(User.builder()
                            .userName(userFormDto.getUserName())
                            .email(userFormDto.getEmail())
                            .password(userFormDto.getPassword())
                            .address(userFormDto.getAddress())
                            .role(Role.USER)
                            .passwordEncoder(passwordEncoder)
                            .build());
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "user/userForm";
        }

        return "redirect:/";
    }

    @GetMapping(value = "/login")
    public String loginUser() {
        return "/user/userLoginForm";
    }

    @GetMapping(value = "/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요.");
        return "/user/userLoginForm";
    }
}
