package com.lanoa.repository;

import com.lanoa.constant.GoodsSellStatus;
import com.lanoa.constant.RackGrade;
import com.lanoa.dto.RackCodeDto;
import com.lanoa.dto.RackCodeSearchDto;
import com.lanoa.entity.*;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

public class RackCodeRepositoryCustomImpl implements RackCodeRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public RackCodeRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression searchRackGradeEq(RackGrade searchRackGrade) {
        return searchRackGrade == null ? null : QRackCode.rackCode.rackGrade.eq(searchRackGrade);
    }

    @Override
    public Page<RackCode> getAdminRackCodePage(RackCodeSearchDto rackCodeSearchDto, Pageable pageable) {
        QueryResults<RackCode> results = queryFactory
                .selectFrom(QRackCode.rackCode)
                .where(searchRackGradeEq(rackCodeSearchDto.getSearchRackGrade()))
                .orderBy(QRackCode.rackCode.rackCodeId.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<RackCode> content = results.getResults();
        long total = results.getTotal();;
        return new PageImpl<>(content, pageable, total);
    }
}
