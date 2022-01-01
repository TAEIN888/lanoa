package com.lanoa.entity;

import com.lanoa.dto.RackFormDto;
import com.lanoa.dto.RackMoveFormDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "TRACK")
@Getter
@NoArgsConstructor
@ToString
public class Rack implements Serializable {

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

    public void updateRackMove(RackMoveFormDto rackMoveFormDto) {
        this.rackQty = this.rackQty + rackMoveFormDto.getMoveQty();
    }
}
