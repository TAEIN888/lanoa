package com.lanoa.repository;

import com.lanoa.dto.RackListDto;
import com.lanoa.dto.RackSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RackRepositoryCustom {

    Page<RackListDto> getRackListPage(RackSearchDto rackSearchDto, Pageable pageable);
}
