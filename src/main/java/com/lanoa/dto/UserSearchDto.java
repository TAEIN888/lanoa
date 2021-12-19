package com.lanoa.dto;

import com.lanoa.constant.GoodsSellStatus;
import com.lanoa.constant.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSearchDto {

    private Role searchUserRole;

    private String searchType;

    private String searchQuery = "";
}
