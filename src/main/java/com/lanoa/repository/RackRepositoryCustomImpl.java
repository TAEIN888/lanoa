package com.lanoa.repository;

import com.lanoa.dto.RackListDto;
import com.lanoa.dto.RackSearchDto;
import com.lanoa.entity.QGoods;
import com.lanoa.entity.QRack;
import com.lanoa.entity.QRackCode;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

public class RackRepositoryCustomImpl implements RackRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public RackRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression searchTypeLike(String searchType, String searchQuery) {

        if (StringUtils.equals("rackCode", searchType)) {
            return QRackCode.rackCode.rackCodeId.like("%" + searchQuery + "%");
        } else if (StringUtils.equals("goodsName", searchType)) {
            return QGoods.goods.goodsName.like("%" + searchQuery + "%");
        }
        return null;
    }

    @Override
    public Page<RackListDto> getRackListPage(RackSearchDto rackSearchDto, Pageable pageable) {
        QueryResults<RackListDto> results = queryFactory
                .from(QRack.rack)
                .leftJoin(QRackCode.rackCode)
                .on(QRack.rack.id.rackCode.rackCodeId.eq(QRackCode.rackCode.rackCodeId))
                .leftJoin(QGoods.goods)
                .on(QRack.rack.id.goodsCode.goodsCode.eq(QGoods.goods.goodsCode))
                .select(Projections.constructor(RackListDto.class,
                        QRackCode.rackCode.rackCodeId,
                        QRackCode.rackCode.rackName,
                        QGoods.goods.goodsCode,
                        QGoods.goods.goodsName,
                        QRack.rack.rackQty))
                .where(searchTypeLike(rackSearchDto.getSearchType(), rackSearchDto.getSearchQuery()), QRack.rack.rackQty.gt(0))
                .orderBy(QGoods.goods.goodsCode.asc())
                .orderBy(QRack.rack.rackQty.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<RackListDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }
}
