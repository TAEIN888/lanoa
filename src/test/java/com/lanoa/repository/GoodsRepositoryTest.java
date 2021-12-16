package com.lanoa.repository;

import com.lanoa.constant.GoodsSellStatus;
import com.lanoa.entity.Goods;
import com.lanoa.entity.QGoods;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class GoodsRepositoryTest {

    @Autowired
    GoodsRepository goodsRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("상품 저장 테스트")
    public void createGoodsTest() {
        Goods savedGoods = goodsRepository.save(Goods.builder()
                .goodsName("테스트 상품")
                .price(10000)
                .goodsDetail("테스트 상품 상세 설명")
                .goodsSellStatus(GoodsSellStatus.SELL)
                .stockQty(300)
                .build());

        System.out.println(savedGoods.toString());
    }

    public void createGoodsList() {
        for (int i = 1; i <= 10; i++) {
            goodsRepository.save(goodsRepository.save(Goods.builder()
                    .goodsCode("1000000" + i)
                    .goodsName("테스트 상품 " + i)
                    .price(10000 + i)
                    .goodsDetail("테스트 상품 상세 설명 " + i)
                    .goodsSellStatus(GoodsSellStatus.SELL)
                    .stockQty(300)
                    .build()));
        }
    }


    public void createGoodsList2() {
        for (int i = 1; i <= 5; i++) {
            goodsRepository.save(goodsRepository.save(Goods.builder()
                    .goodsCode("1000000" + i)
                    .goodsName("테스트 상품 " + i)
                    .price(10000 + i)
                    .goodsDetail("테스트 상품 상세 설명 " + i)
                    .goodsSellStatus(GoodsSellStatus.SELL)
                    .stockQty(100)
                    .build()));
        }

        for (int i = 6; i <= 10; i++) {
            goodsRepository.save(goodsRepository.save(Goods.builder()
                    .goodsCode("1000000" + i)
                    .goodsName("테스트 상품 " + i)
                    .price(10000 + i)
                    .goodsDetail("테스트 상품 상세 설명 " + i)
                    .goodsSellStatus(GoodsSellStatus.SOLD_OUT)
                    .stockQty(0)
                    .build()));
        }
    }

    @Test
    @DisplayName("상품명 조회 테스트")
    public void findByGoodsNameTest() {
        this.createGoodsList();
        List<Goods> goodsList = goodsRepository.findByGoodsName("테스트 상품 1");
        for (Goods goods : goodsList) {
            System.out.println(goods.toString());
        }
    }

    @Test
    @DisplayName("상품명, 상품상세 or 테스트")
    public void findByGoodsNameOrGoodsDetailTest() {
        this.createGoodsList();
        List<Goods> goodsList = goodsRepository.findByGoodsNameOrGoodsDetail("테스트 상품 1", "테스트 상품 상세 설명 5");

        for (Goods goods : goodsList) {
            System.out.println(goods.toString());
        }
    }

    @Test
    @DisplayName("상품가격 LessThan 테스트")
    public void findByPriceLessThanTest() {
        this.createGoodsList();
        List<Goods> goodsList = goodsRepository.findByPriceLessThan(10005);

        for (Goods goods : goodsList) {
            System.out.println(goods.toString());
        }
    }

    @Test
    @DisplayName("상품가격 LessThan Order By DESC 테스트")
    public void findByPriceLessThanOrderByPriceDescTest() {
        this.createGoodsList();
        List<Goods> goodsList = goodsRepository.findByPriceLessThanOrderByPriceDesc(10005);

        for (Goods goods : goodsList) {
            System.out.println(goods.toString());
        }
    }

    @Test
    @DisplayName("@Query를 이용한 상품 조회 테스트")
    public void findByGoodsDetailTest() {
        this.createGoodsList();
        List<Goods> goodsList = goodsRepository.findByGoodsDetail("테스트 상품 상세 설명");
        for (Goods goods : goodsList) {
            System.out.println(goods.toString());
        }
    }

    @Test
    @DisplayName("@Query Native Query를 이용한 상품 조회 테스트")
    public void findByGoodsDetailByNativeTest() {
        this.createGoodsList();
        List<Goods> goodsList = goodsRepository.findByGoodsDetailByNative("테스트 상품 상세 설명");
        for (Goods goods : goodsList) {
            System.out.println(goods.toString());
        }
    }

    @Test
    @DisplayName("Querydsl 조회 테스트1")
    public void queryDslTest() {
        this.createGoodsList();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QGoods qGoods = QGoods.goods;

        JPAQuery<Goods> query = queryFactory.selectFrom(qGoods)
                .where(qGoods.goodsSellStatus.eq(GoodsSellStatus.SELL))
                .where(qGoods.goodsDetail.like("%" + "테스트 상품 상세 설명" + "%"))
                .orderBy(qGoods.price.desc());

        List<Goods> goodsList = query.fetch();

        for (Goods goods : goodsList) {
            System.out.println(goods.toString());
        }
    }

    @Test
    @DisplayName("상품 Querydsl 조회 테스트2")
    public void queryDslTest2() {

        this.createGoodsList2();

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        QGoods goods = QGoods.goods;
        String goodsDetail = "테스트 상품 상세 설명";
        int price = 10003;
        String goodsSellStatus = "SELL";

        booleanBuilder.and(goods.goodsDetail.like("%" + goodsDetail + "%"));
        booleanBuilder.and(goods.price.gt(price));

        if (StringUtils.equals(goodsSellStatus, GoodsSellStatus.SELL)) {
            booleanBuilder.and(goods.goodsSellStatus.eq(GoodsSellStatus.SELL));
        }

        Pageable pageable = PageRequest.of(0, 5);
        Page<Goods> goodsPagingResult = goodsRepository.findAll(booleanBuilder, pageable);
        System.out.println("total elements : " + goodsPagingResult.getTotalElements());

        List<Goods> resultGoodsList = goodsPagingResult.getContent();

        for (Goods resultGoods : resultGoodsList) {
            System.out.println(resultGoods.toString());
        }

    }
}
