package com.lanoa.entity;

import com.lanoa.constant.GoodsSellStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "GOODS")
@Getter
@Setter
@ToString
public class Goods {

    @Id
    @Column(name = "GOODS_CODE")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long goodsCode; // 상품 코드

    @Column(nullable = false, length = 100)
    private String goodsName; // 상품명

    @Column(name = "PRICE", nullable = false)
    private int price; // 상품 가격

    @Column(nullable = false)
    private int stockQty; // 재고 수량

    @Lob
    @Column(nullable = false)
    private String goodsDetail; // 상품 상세

    @Enumerated(EnumType.STRING)
    private GoodsSellStatus goodsSellStatus; // 상품 판매 상태

    private LocalDateTime regTime;
    private LocalDateTime updateTime;
}
