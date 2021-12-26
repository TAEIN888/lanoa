package com.lanoa.entity;

import com.lanoa.constant.RackGrade;
import com.lanoa.dto.RackCodeFormDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TRACKCODE")
@Getter
@NoArgsConstructor
@ToString
public class RackCode extends BaseEntity {

    @Id
    @Column(nullable = false, length = 7, unique = true, name = "RACK_CODE")
    private String rackCodeId; // 랙코드

    @Column(nullable = false, length = 100)
    private String rackName; // 랙코드명

    @Column(nullable = false)
    private RackGrade rackGrade; // 랙 등급

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "RACK_CODE")
    private List<Rack> rackList = new ArrayList<>();

    @Builder
    public RackCode(String rackCodeId, String rackName, RackGrade rackGrade) {
        this.rackCodeId = rackCodeId;
        this.rackName = rackName;
        this.rackGrade = rackGrade;
    }

    public void updateRackCode(RackCodeFormDto rackCodeFormDto) {
        this.rackName = rackCodeFormDto.getRackName();
        this.rackGrade = rackCodeFormDto.getRackGrade();
    }
}
