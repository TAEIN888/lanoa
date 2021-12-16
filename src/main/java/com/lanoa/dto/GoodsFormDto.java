package com.lanoa.dto;

import com.lanoa.constant.GoodsSellStatus;
import com.lanoa.entity.Goods;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class GoodsFormDto {

    private String goodsCode;

    @NotNull(message = "상품명을 입력해주세요.")
    private String goodsName; // 상품명

    @NotNull(message = "상품 가격을 입력해주세요.")
    private int price;

    @NotNull(message = "재고 수량 값을 입력해주세요.")
    private int stockQty; // 재고 수량

    @NotNull(message = "상품 상세 값을 입력해주세요.")
    private String goodsDetail; // 상품 상세

    private GoodsSellStatus goodsSellStatus; // 상품 판매 상태

    private static ModelMapper modelMapper = new ModelMapper();

    public Goods createGoods() {
        modelMapper.getConfiguration()
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true);
        return modelMapper.map(this, Goods.class);
    }

    public static GoodsFormDto of(Goods goods) {
        return modelMapper.map(goods, GoodsFormDto.class);
    }
}
