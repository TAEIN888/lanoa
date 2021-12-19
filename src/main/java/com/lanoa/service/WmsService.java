package com.lanoa.service;

import com.lanoa.dto.GoodsFormDto;
import com.lanoa.dto.RackCodeFormDto;
import com.lanoa.dto.RackCodeSearchDto;
import com.lanoa.entity.Goods;
import com.lanoa.entity.RackCode;
import com.lanoa.repository.RackCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class WmsService {

    private final RackCodeRepository rackCodeRepository;

    public String saveRackCode(RackCodeFormDto rackCodeFormDto) throws Exception {
        RackCode rackCode = rackCodeFormDto.createRackCode();
        rackCodeRepository.save(rackCode);

        return rackCode.getRackCodeId();
    }


    @Transactional(readOnly = true)
    public Page<RackCode> getAdminRackCodePage(RackCodeSearchDto rackCodeSearchDto, Pageable pageable) {
        return rackCodeRepository.getAdminRackCodePage(rackCodeSearchDto, pageable);
    }
}
