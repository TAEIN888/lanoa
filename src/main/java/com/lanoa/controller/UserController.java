package com.lanoa.controller;

import com.lanoa.constant.Role;
import com.lanoa.dto.*;
import com.lanoa.entity.User;
import com.lanoa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping(value = "createAdminUser")
    public String createAdminuser(Model model) {
        try {
            userService.saveUser(User.builder()
                    .userName("관리자")
                    .email("pro_cess@naver.com")
                    .password("xodls1597")
                    .address("서울")
                    .role(Role.ADMIN)
                    .passwordEncoder(passwordEncoder)
                    .build());
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "redirect:/";
        }
        return "redirect:/";
    }

    @GetMapping(value = "admin/users/new")
    public String userForm(Model model) {
        model.addAttribute("userFormDto", new UserFormDto());

        return "user/userForm";
    }

    @PostMapping(value = "admin/users/new")
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

        return this.userAdminPage(new UserSearchDto(), Optional.of(0), model);
    }

    @GetMapping(value = {"/admin/userlist", "/admin/userlist/{page}"})
    public String userAdminPage(UserSearchDto userSearchDto, @PathVariable("page") Optional<Integer> page, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
        Page<User> userList = userService.getAdminUserList(userSearchDto, pageable);

        model.addAttribute("userList", userList);
        model.addAttribute("userSearchDto", userSearchDto);
        model.addAttribute("maxPage", 5);

        return "user/userManage";
    }

    @GetMapping(value = "/admin/users/{userNumber}")
    public String getUserInfo(@PathVariable("userNumber") Long userNumber, Model model) {
        try {
            UserUpdateFormDto userUpdateFormDto = userService.getUserInfo(userNumber);
            model.addAttribute("userUpdateFormDto", userUpdateFormDto);
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", "존재하지 않는 사용자입니다.");
            model.addAttribute("userFormDto", new UserFormDto());
            return "user/userForm";
        }

        return "user/userUpdateForm";
    }

    @PostMapping(value = "/admin/users/{userNumber}")
    public String updateUserInfo(@Valid UserUpdateFormDto userUpdateFormDto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "user/userForm";
        }

        try {
            userService.updateUser(userUpdateFormDto);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "사용자 정보 수정 중 오류가 발생하였습니다.");
            e.printStackTrace();
        }

        return this.userAdminPage(new UserSearchDto(), Optional.of(0), model);
    }

    @GetMapping(value = "/users/login")
    public String loginUser() {
        return "/user/userLoginForm";
    }

    @GetMapping(value = "/users/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "이메일 또는 비밀번호를 확인해주세요.");
        return "/user/userLoginForm";
    }
}
