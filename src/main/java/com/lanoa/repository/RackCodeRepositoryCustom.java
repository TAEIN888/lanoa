package com.lanoa.repository;

import com.lanoa.dto.RackCodeSearchDto;
import com.lanoa.entity.RackCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RackCodeRepositoryCustom {

    Page<RackCode> getAdminRackCodePage(RackCodeSearchDto rackCodeSearchDto, Pageable pageable);
}
