package com.lanoa.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class GoodsDto {

    private Long goodsCode;
    private String goodsName;
    private Integer price;
    private String goodsDetail;
    private String sellStatCd;
    private LocalDateTime regTime;
    private LocalDateTime updateTime;
}
