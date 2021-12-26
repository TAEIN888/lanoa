package com.lanoa.entity;

import com.lanoa.dto.RackFormDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "TRACK")
//@IdClass(RackId.class)
@Getter
@NoArgsConstructor
@ToString
public class Rack implements Serializable {
//
//    @Id
//    private String rackCode;
//
//    @Id
//    private String goodsCode;

    @EmbeddedId
    private RackId id;

    @Column(nullable = false)
    private Long rackQty; // 적재 수량

    @Builder
    public Rack(String rackCodeId, String goodsCode, Long rackQty) {
        RackCode rackCode = RackCode.builder()
                .rackCodeId(rackCodeId)
                .build();
        Goods goods = Goods.builder()
                .goodsCode(goodsCode)
                .build();
        RackId rackId = new RackId(rackCode, goods);
        this.id = rackId;
        this.rackQty = rackQty == null ? 0 : rackQty;
    }

    public void updateRack(RackFormDto rackFormDto) {
        this.rackQty = this.rackQty + rackFormDto.getRackQty();
    }
}
