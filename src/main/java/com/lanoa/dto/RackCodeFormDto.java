package com.lanoa.dto;

import com.lanoa.constant.RackGrade;
import com.lanoa.entity.RackCode;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class RackCodeFormDto {

    @NotEmpty(message = "랙코드를 입력해주세요.")
    private String rackCodeId;

    @NotEmpty(message = "랙코드명을 입력해주세요.")
    private String rackName;

    private RackGrade rackGrade;

    private static ModelMapper modelMapper = new ModelMapper();

    public RackCode createRackCode() {
        modelMapper.getConfiguration()
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true);
        return modelMapper.map(this, RackCode.class);
    }

    public static RackCodeFormDto of(RackCode rackCode) {
        return modelMapper.map(rackCode, RackCodeFormDto.class);
    }
}
