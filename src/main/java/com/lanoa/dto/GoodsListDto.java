package com.lanoa.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lanoa.constant.GoodsSellStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class GoodsListDto {

    private String goodsCode;

    private String goodsName; // 상품명

    private int price;

    private GoodsSellStatus goodsSellStatus; // 상품 판매 상태

    private String userName;

    private LocalDateTime createdDate;
}
