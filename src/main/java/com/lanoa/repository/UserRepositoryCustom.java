package com.lanoa.repository;

import com.lanoa.dto.UserSearchDto;
import com.lanoa.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepositoryCustom {

    Page<User> getAdminUserList(UserSearchDto userSearchDto, Pageable pageable);
}
