package com.lanoa.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RackSearchDto {

    private String searchType;

    private String searchQuery = "";
}
