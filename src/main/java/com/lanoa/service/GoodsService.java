package com.lanoa.service;

import com.lanoa.dto.GoodsFormDto;
import com.lanoa.entity.Goods;
import com.lanoa.repository.GoodsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class GoodsService {

    private final GoodsRepository goodsRepository;

    public String saveGoods(GoodsFormDto goodsFormDto) throws Exception {
        Goods goods = goodsFormDto.createGoods();
        goodsRepository.save(goods);

        return goods.getGoodsCode();
    }

    @Transactional(readOnly = true)
    public GoodsFormDto getGoodsInfo(String goodsCode) {
        Goods goods = goodsRepository.findByGoodsCode(goodsCode);

        if (goods == null) {
            throw new EntityNotFoundException();
        }
        GoodsFormDto goodsFormDto = GoodsFormDto.of(goods);

        return goodsFormDto;
    }

    public String updateGoods(GoodsFormDto goodsFormDto) throws Exception {
        Goods goods = goodsRepository.findByGoodsCode(goodsFormDto.getGoodsCode());

        if (goods == null) {
            throw new EntityNotFoundException();
        }
        goods.updateGoods(goodsFormDto);

        return goods.getGoodsCode();
    }
}
