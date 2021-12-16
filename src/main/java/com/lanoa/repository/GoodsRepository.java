package com.lanoa.repository;

import com.lanoa.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GoodsRepository extends JpaRepository<Goods, Long>, QuerydslPredicateExecutor<Goods> {

    Goods findByGoodsCode(String goodsCode);
    List<Goods> findByGoodsName(String goodsName);

    List<Goods> findByGoodsNameOrGoodsDetail(String goodsName, String goodsDetail);

    List<Goods> findByPriceLessThan(Integer price);
    List<Goods> findByPriceLessThanOrderByPriceDesc(Integer price);

    @Query("select i from Goods i where i.goodsDetail like %:goodsDetail% order by i.price desc")
    List<Goods> findByGoodsDetail(@Param("goodsDetail") String goodsDetail);

    @Query(value = "select * from Goods i where i.goods_detail like %:goodsDetail% order by i.price desc", nativeQuery = true)
    List<Goods> findByGoodsDetailByNative(@Param("goodsDetail") String goodsDetail);
}