package com.lanoa.entity;

import com.lanoa.constant.Role;
import com.lanoa.dto.UserFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name = "USER")
@Getter
@Setter
@ToString
public class User {

    @Id
    @Column(name = "USER_NUMBER")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userNumber;

    private String userName;

    @Column(unique = true)
    private String email;

    private String password;

    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

    public static User createUser(UserFormDto userFormDto, PasswordEncoder passwordEncoder) {
        User user = new User();

        user.setUserName(userFormDto.getUserName());
        user.setEmail(userFormDto.getEmail());
        user.setAddress(userFormDto.getAddress());

        String password = passwordEncoder.encode(userFormDto.getPassword());
        user.setPassword(password);
        user.setRole(Role.USER);
        return user;
    }
}
