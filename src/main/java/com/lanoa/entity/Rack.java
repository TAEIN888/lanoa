package com.lanoa.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "TRACK")
@IdClass(RackId.class)
@Getter
@NoArgsConstructor
@ToString
public class Rack extends BaseTimeEntity implements Serializable {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rack_code")
    private RackCode rackCode;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_code")
    private Goods goodsCode;


    @Column(nullable = false)
    private int rackQty; // 적재 수량

    @Builder
    public Rack(RackCode rackCode, Goods goods, int rackQty) {
        this.rackCode = rackCode;
        this.goodsCode = goods;
        this.rackQty = rackQty;
    }
}
