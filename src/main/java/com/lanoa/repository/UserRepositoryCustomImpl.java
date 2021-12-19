package com.lanoa.repository;

import com.lanoa.constant.Role;
import com.lanoa.dto.UserSearchDto;
import com.lanoa.entity.QUser;
import com.lanoa.entity.User;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public UserRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression searchUserRoleEq(Role role) {
        return role == null ? null : QUser.user.role.eq(role);
    }

    private BooleanExpression searchTypeLike(String searchType, String searchQuery) {
        if (StringUtils.equals("userName", searchType)) {
            return QUser.user.userName.like("%" + searchQuery + "%");
        } else if (StringUtils.equals("email", searchType)) {
            return QUser.user.email.like("%" + searchQuery + "%");
        }
        return null;
    }

    @Override
    public Page<User> getAdminUserList(UserSearchDto userSearchDto, Pageable pageable) {
        QueryResults<User> results = queryFactory
                .selectFrom(QUser.user)
                .where(searchUserRoleEq(userSearchDto.getSearchUserRole()),
                        searchTypeLike(userSearchDto.getSearchType(), userSearchDto.getSearchQuery()))
                .orderBy(QUser.user.userNumber.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<User> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }
}
