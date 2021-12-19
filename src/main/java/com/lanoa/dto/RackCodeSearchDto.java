package com.lanoa.dto;

import com.lanoa.constant.RackGrade;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RackCodeSearchDto {

    private RackGrade searchRackGrade;

    private String searchType;

    private String searchQuery = "";
}
