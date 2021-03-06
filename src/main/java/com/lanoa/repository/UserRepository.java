package com.lanoa.repository;

import com.lanoa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    User findByEmail(String email);
}
