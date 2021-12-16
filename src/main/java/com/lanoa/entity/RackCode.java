package com.lanoa.entity;

import com.lanoa.constant.RackGrade;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "TRACKCODE")
@Getter
@NoArgsConstructor
@ToString
public class RackCode extends BaseEntity {

    @Id
    @Column(nullable = false, length = 7, unique = true, name = "RACK_CODE")
    private String rackCode; // 랙코드

    @Column(nullable = false, length = 100)
    private String rackName; // 랙코드명

    @Column(nullable = false, length = 1)
    private RackGrade rackGrade; // 랙 등급

    @Builder
    public RackCode(String rackCode, String rackName, RackGrade rackGrade) {
        this.rackCode = rackCode;
        this.rackName = rackName;
        this.rackGrade = rackGrade;
    }

}
