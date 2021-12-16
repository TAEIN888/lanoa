package com.lanoa.entity;


import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class RackId implements Serializable {

    private String rackCode;

    private String goodsCode;
}
