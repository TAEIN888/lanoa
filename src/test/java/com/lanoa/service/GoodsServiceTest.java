package com.lanoa.service;

import com.lanoa.constant.GoodsSellStatus;
import com.lanoa.dto.GoodsFormDto;
import com.lanoa.entity.Goods;
import com.lanoa.repository.GoodsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class GoodsServiceTest {

    @Autowired
    GoodsService goodsService;

    @Autowired
    GoodsRepository goodsRepository;

    @Test
    @DisplayName("상품 등록 테스트")
    @WithMockUser(username = "ADMIN", roles = "ADMIN")
    public void saveGoods() throws Exception {
        GoodsFormDto goodsFormDto = new GoodsFormDto();
        goodsFormDto.setGoodsName("테스트 상품");
        goodsFormDto.setGoodsSellStatus(GoodsSellStatus.SELL);
        goodsFormDto.setGoodsDetail("테스트 상품 입니다.");
        goodsFormDto.setPrice(1000);
        goodsFormDto.setStockQty(100);

        String goodsCode = goodsService.saveGoods(goodsFormDto);

        Goods goods = goodsRepository.findByGoodsCode(goodsCode);
        if (goods == null) {
            throw new EntityNotFoundException();
        }
        assertEquals(goodsFormDto.getGoodsName(), goods.getGoodsName());
        assertEquals(goodsFormDto.getGoodsSellStatus(), goods.getGoodsSellStatus());
        assertEquals(goodsFormDto.getGoodsDetail(), goods.getGoodsDetail());
        assertEquals(goodsFormDto.getPrice(), goods.getPrice());
        assertEquals(goodsFormDto.getStockQty(), goods.getStockQty());
    }
}