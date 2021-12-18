package com.lanoa.dto;

import com.lanoa.constant.GoodsSellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodsSearchDto {

    private String searchDateType;

    private GoodsSellStatus searchSellStatus;

    private String searchType;

    private String searchQuery = "";
}
