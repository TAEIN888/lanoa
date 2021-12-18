package com.lanoa.repository;

import com.lanoa.constant.GoodsSellStatus;
import com.lanoa.dto.GoodsListDto;
import com.lanoa.dto.GoodsSearchDto;
import com.lanoa.entity.Goods;
import com.lanoa.entity.QGoods;
import com.lanoa.entity.QUser;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class GoodsRepositoryCustomImpl implements GoodsRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public GoodsRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression searchSellStatusEq(GoodsSellStatus searchSellStatus) {
        return searchSellStatus == null ? null : QGoods.goods.goodsSellStatus.eq(searchSellStatus);
    }

    private BooleanExpression regDateAfter(String searchDateType) {
        LocalDateTime dateTime = LocalDateTime.now();

        if (StringUtils.equals("all", searchDateType) || searchDateType == null) {
            return null;
        } else if (StringUtils.equals("1D", searchDateType)) {
            dateTime = dateTime.minusDays(1);
        } else if (StringUtils.equals("1W", searchDateType)) {
            dateTime = dateTime.minusWeeks(1);
        } else if (StringUtils.equals("1M", searchDateType)) {
            dateTime = dateTime.minusMonths(1);
        } else if (StringUtils.equals("6M", searchDateType)) {
            dateTime = dateTime.minusMonths(6);
        }

        return QGoods.goods.createdDate.after(dateTime);
    }

    private BooleanExpression searchTypeLike(String searchType, String searchQuery) {

        if (StringUtils.equals("goodsName", searchType)) {
            return QGoods.goods.goodsName.like("%" + searchQuery + "%");
        } else if (StringUtils.equals("createdBy", searchType)) {
            return QGoods.goods.createdBy.eq(JPAExpressions
                    .select(QUser.user.email)
                    .from(QUser.user)
                    .where(QUser.user.userName.like("%" + searchQuery + "%")));
        }
        return null;
    }

    @Override
    public Page<GoodsListDto> getAdminGoodsPage(GoodsSearchDto goodsSearchDto, Pageable pageable) {
        QueryResults<GoodsListDto> results = queryFactory
                .from(QGoods.goods)
                .leftJoin(QUser.user)
                .select(Projections.constructor(GoodsListDto.class,
                                QGoods.goods.goodsCode,
                                QGoods.goods.goodsName,
                                QGoods.goods.price,
                                QGoods.goods.goodsSellStatus,
                                QUser.user.userName,
                                //Expressions.dateTemplate(LocalDateTime.class, "to_date({0}, {1})", QGoods.goods.createdDate, "yyyy-MM-dd HH:mm:ss")))
                                QGoods.goods.createdDate))
                                //Expressions.stringTemplate("DATE_FORMAT({0}, {1})", QGoods.goods.createdDate, "%Y-%m-%d %H:%i")))
                .on(QGoods.goods.createdBy.eq(QUser.user.email))
                .where(regDateAfter(goodsSearchDto.getSearchDateType()),
                        searchSellStatusEq(goodsSearchDto.getSearchSellStatus()),
                        searchTypeLike(goodsSearchDto.getSearchType(), goodsSearchDto.getSearchQuery()))
                .orderBy(QGoods.goods.goodsCode.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<GoodsListDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }
}
