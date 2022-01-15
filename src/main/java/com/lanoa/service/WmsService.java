package com.lanoa.service;

import com.lanoa.dto.*;
import com.lanoa.entity.Goods;
import com.lanoa.entity.Rack;
import com.lanoa.entity.RackCode;
import com.lanoa.entity.RackId;
import com.lanoa.repository.GoodsRepository;
import com.lanoa.repository.RackCodeRepository;
import com.lanoa.repository.RackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class WmsService {

    private final RackCodeRepository rackCodeRepository;

    private final RackRepository rackRepository;

    private final GoodsRepository goodsRepository;

    public String saveRackCode(RackCodeFormDto rackCodeFormDto) throws Exception {
        RackCode rackCode = rackCodeFormDto.createRackCode();
        rackCodeRepository.save(rackCode);

        return rackCode.getRackCodeId();
    }

    @Transactional(readOnly = true)
    public Page<RackCode> getAdminRackCodePage(RackCodeSearchDto rackCodeSearchDto, Pageable pageable) {
        return rackCodeRepository.getAdminRackCodePage(rackCodeSearchDto, pageable);
    }

    @Transactional(readOnly = true)
    public RackCodeFormDto getRackCodeInfo(String rackCodeId) {
        RackCode rackCode = rackCodeRepository.findByRackCodeId(rackCodeId);

        if (rackCode == null) {
            throw new EntityNotFoundException();
        }
        RackCodeFormDto rackCodeFormDto = RackCodeFormDto.of(rackCode);

        return rackCodeFormDto;
    }

    public String updateRackCode(RackCodeFormDto rackCodeFormDto) throws Exception {
        RackCode rackCode = rackCodeRepository.findByRackCodeId(rackCodeFormDto.getRackCodeId());

        if (rackCode == null) {
            throw new EntityNotFoundException();
        }

        rackCode.updateRackCode(rackCodeFormDto);

        return rackCode.getRackCodeId();
    }

    public String rackWarehousing(RackFormDto rackFormDto) throws Exception {

        RackCode rackCode = RackCode
                .builder()
                .rackCodeId(rackFormDto.getRackCode())
                .build();

        Goods goods = Goods.builder()
                .goodsCode(rackFormDto.getGoodsCode())
                .build();

        Goods goods1 = goodsRepository.findByGoodsCode(rackFormDto.getGoodsCode());

        if (goods1 == null) {
            throw new EntityNotFoundException();
        }

        RackId rackId = new RackId(rackCode, goods);
        Rack rack = rackRepository.findById(rackId)
                .orElseGet(() -> createRack(rackFormDto));

        rack.updateRack(rackFormDto);

        return rack.getId().getRackCode().getRackCodeId();
    }

    private Rack createRack(RackFormDto rackFormDto) {
        Rack savedRack = rackRepository.save(Rack.builder()
                .rackCodeId(rackFormDto.getRackCode())
                .goodsCode(rackFormDto.getGoodsCode())
                .rackQty(Long.valueOf(0))
                .build());

        return savedRack;
    }

    @Transactional(readOnly = true)
    public Page<RackListDto> getRackListPage(RackSearchDto rackSearchDto, Pageable pageable) {
        return rackRepository.getRackListPage(rackSearchDto, pageable);
    }

    public String rackMove(RackMoveFormDto rackMoveFormDto) throws Exception {

        RackCode fromRackCode = RackCode
                .builder()
                .rackCodeId(rackMoveFormDto.getRackCode())
                .build();

        Goods goods = Goods.builder()
                .goodsCode(rackMoveFormDto.getGoodsCode())
                .build();

        RackId fromRackId = new RackId(fromRackCode, goods);
        Rack fromRack = rackRepository.findById(fromRackId)
                .orElseThrow(EntityNotFoundException::new);

        RackCode toRackCode = RackCode
                .builder()
                .rackCodeId(rackMoveFormDto.getMoveRackCode())
                .build();

        RackId toRackId = new RackId(toRackCode, goods);
        Rack toRack = rackRepository.findById(toRackId)
                .orElseThrow(EntityNotFoundException::new);

        toRack.updateRackMove(rackMoveFormDto);

        rackMoveFormDto.setMoveQty(rackMoveFormDto.getMoveQty() * -1);
        fromRack.updateRackMove(rackMoveFormDto);

        if (fromRack.getRackQty() < 0) {
            rackMoveFormDto.setMoveQty(rackMoveFormDto.getMoveQty() * -1);
            throw new Exception();
        }

        return toRackCode.getRackCodeId();
    }

    public String rackOut(RackOutFormDto rackOutFormDto) throws Exception {

        RackCode rackCode = RackCode
                .builder()
                .rackCodeId(rackOutFormDto.getRackCode())
                .build();

        Goods goods = Goods.builder()
                .goodsCode(rackOutFormDto.getGoodsCode())
                .build();

        RackId rackId = new RackId(rackCode, goods);
        Rack rack = rackRepository.findById(rackId)
                .orElseThrow(EntityNotFoundException::new);

        rack.updateRackOut(rackOutFormDto);

        return rackCode.getRackCodeId();
    }
}
