package com.lanoa.service;

import com.lanoa.dto.*;
import com.lanoa.entity.Goods;
import com.lanoa.entity.User;
import com.lanoa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public User saveUser(User user) {
        validateDuplicateUser(user);

        return userRepository.save(user);
    }

    private void validateDuplicateUser(User user) {
        User findUser = userRepository.findByEmail(user.getEmail());

        if (findUser != null) {
            throw new IllegalStateException("이미 등록된 사용자입니다.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException(email);
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().toString())
                .build();
    }

    @Transactional(readOnly = true)
    public Page<User> getAdminUserList(UserSearchDto userSearchDto, Pageable pageable) {
        return userRepository.getAdminUserList(userSearchDto, pageable);
    }

    @Transactional(readOnly = true)
    public UserUpdateFormDto getUserInfo(Long userNumber) {
        User user = userRepository.findById(userNumber)
                .orElseThrow(EntityNotFoundException::new);

        UserUpdateFormDto userUpdateFormDto = UserUpdateFormDto.of(user);

        return userUpdateFormDto;
    }

    public Long updateUser(UserUpdateFormDto userUpdateFormDto) throws Exception {
        User user = userRepository.findById(userUpdateFormDto.getUserNumber())
                .orElseThrow(EntityNotFoundException::new);

        user.updateUser(userUpdateFormDto);

        return user.getUserNumber();
    }
}
