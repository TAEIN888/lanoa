package com.lanoa.repository;

import com.lanoa.dto.GoodsListDto;
import com.lanoa.dto.GoodsSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GoodsRepositoryCustom {

    Page<GoodsListDto> getAdminGoodsPage(GoodsSearchDto goodsSearchDto, Pageable pageable);
}
