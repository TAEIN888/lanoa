package com.lanoa.entity;

import com.lanoa.constant.GoodsSellStatus;
import com.lanoa.dto.GoodsFormDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@Entity
@Table(name = "TGOODS")
@Getter
@NoArgsConstructor
@ToString
public class Goods extends BaseEntity {

    @Id
    @GenericGenerator(
        name = "idGenerator",
        strategy = "com.lanoa.entity.IdGenerator",
        parameters = {
            @Parameter(name = IdGenerator.METHOD, value = "GOODS")
        }
    )
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "GOODS_CODE", length = 8)
    private String goodsCode; // 상품 코드

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

    @Builder
    public Goods(String goodsCode, String goodsName, Integer price, Integer stockQty, String goodsDetail, GoodsSellStatus goodsSellStatus) {
        this.goodsCode = goodsCode;
        this.goodsName = goodsName;
        this.price = price;
        this.stockQty = stockQty;
        this.goodsDetail = goodsDetail;
        this.goodsSellStatus = goodsSellStatus;
    }

    public void updateGoods(GoodsFormDto goodsFormDto) {
        this.goodsName = goodsFormDto.getGoodsName();
        this.price = goodsFormDto.getPrice();
        this.stockQty = goodsFormDto.getStockQty();
        this.goodsDetail = goodsFormDto.getGoodsDetail();
        this.goodsSellStatus = goodsFormDto.getGoodsSellStatus();
    }
}
