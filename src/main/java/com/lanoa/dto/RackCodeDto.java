package com.lanoa.dto;

import com.lanoa.constant.RackGrade;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RackCodeDto {

    private String rackCodeId;
    private String rackName;
    private RackGrade rackGrade;
}
