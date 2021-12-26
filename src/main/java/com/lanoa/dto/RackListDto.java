package com.lanoa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RackListDto {

    private String rackCodeId;

    private String rackName;

    private String goodsCode;

    private String goodsName;

    private Long rackQty;
}
