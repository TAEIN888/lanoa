package com.lanoa.dto;

import com.lanoa.constant.Role;
import com.lanoa.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UserUpdateFormDto {

    private Long userNumber;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String userName;

    private String email;

    @NotEmpty(message = "주소는 필수 입력 값입니다.")
    private String address;

    private Role role;

    private static ModelMapper modelMapper = new ModelMapper();

    public static UserUpdateFormDto of(User user) {
        return modelMapper.map(user, UserUpdateFormDto.class);
    }
}
