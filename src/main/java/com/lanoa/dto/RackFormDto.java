package com.lanoa.dto;

import com.lanoa.entity.Rack;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RackFormDto {

    @NotNull(message = "랙코드 값을 입력해주세요.")
    private String rackCode;

    @NotNull(message = "상품코드 값을 입력해주세요.")
    private String goodsCode;

    @NotNull(message = "입고 수량을 입력해주세요.")
    private Long rackQty;

    private static ModelMapper modelMapper = new ModelMapper();

    public Rack createRack() {
        modelMapper.getConfiguration()
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true);
        return modelMapper.map(this, Rack.class);
    }

    public static RackFormDto of(Rack rack) {
        return modelMapper.map(rack, RackFormDto.class);
    }
}
