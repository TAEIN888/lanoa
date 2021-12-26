package com.lanoa.repository;

import com.lanoa.constant.GoodsSellStatus;
import com.lanoa.constant.RackGrade;
import com.lanoa.entity.Goods;
import com.lanoa.entity.Rack;
import com.lanoa.entity.RackCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class RackRepositoryTest {

    @Autowired
    RackRepository rackRepository;

    @Autowired
    GoodsRepository goodsRepository;

    @Autowired
    RackCodeRepository rackCodeRepository;

    private Goods createGoods() {
        Goods savedGoods = goodsRepository.save(Goods.builder()
                .goodsName("테스트 상품")
                .price(10000)
                .goodsDetail("테스트 상품 상세 설명")
                .goodsSellStatus(GoodsSellStatus.SELL)
                .stockQty(300)
                .build());

        return savedGoods;
    }

    private RackCode createRackCode() {
        RackCode savedRackCode = rackCodeRepository.save(RackCode.builder()
                .rackCodeId("A010101")
                .rackGrade(RackGrade.NORMAL)
                .rackName("정상A랙")
                .build());

        return savedRackCode;
    }

    @Test
    @DisplayName("랙 저장 테스트")
    public void createRackTest() {
        Goods goods = createGoods();
        RackCode rackCode = createRackCode();
        Rack savedRack = rackRepository.save(Rack.builder()
                .rackCodeId(rackCode.getRackCodeId())
                .goodsCode(goods.getGoodsCode())
                .rackQty(Long.valueOf(0))
                .build());
        System.out.println(savedRack.toString());
    }
//
//    @Test
//    @DisplayName("랙 저장 테스트")
//    public void selectRackTest() {
//        Goods goods = createGoods();
//        RackCode rackCode = createRackCode();
//        Rack savedRack = rackRepository.save(Rack.builder()
//                .rackCode(rackCode)
//                .goods(goods)
//                .rackQty(Long.valueOf(0))
//                .build());
//
//        List<Rack> rackList = rackRepository.findByRackCode(rackCode);
//
//        for (Rack rack : rackList) {
//            System.out.println(rack.toString());
//        }
//
//    }
}
