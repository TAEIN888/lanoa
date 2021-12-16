package com.lanoa.entity;

import com.lanoa.constant.Role;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name = "TUSER")
@Getter
@NoArgsConstructor
@ToString
public class User extends BaseEntity {

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

    @Builder
    public User(String userName, String email, String password, String address, Role role, PasswordEncoder passwordEncoder) {
        this.userName = userName;
        this.email = email;
        this.password = passwordEncoder.encode(password);
        this.address = address;
        this.role = role;
    }
}
