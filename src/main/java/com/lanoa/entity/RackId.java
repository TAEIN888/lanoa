package com.lanoa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class RackId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "RACK_CODE")
    private RackCode rackCode;

    @ManyToOne
    @JoinColumn(name = "GOODS_CODE")
    private Goods goodsCode;
}
