package com.lanoa.dto;

import com.lanoa.entity.Rack;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RackOutFormDto {

    @NotEmpty(message = "랙코드를 입력해주세요.")
    private String rackCode;

    @NotEmpty(message = "상품코드를 입력해주세요.")
    private String goodsCode;

    @NotNull(message = "재고 수량을 입력해주세요.")
    private Long rackQty;

    @NotNull(message = "출고 수량을 입력해주세요.")
    @Min(value = 1, message = "출고 수량은 1 이상이어야 합니다.")
    private Long outQty;

    private static ModelMapper modelMapper = new ModelMapper();

    public Rack createRack() {
        modelMapper.getConfiguration()
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true);
        return modelMapper.map(this, Rack.class);
    }

    public static RackOutFormDto of(Rack rack) {
        return modelMapper.map(rack, RackOutFormDto.class);
    }
}
