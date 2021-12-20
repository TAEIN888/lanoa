package com.lanoa.repository;

import com.lanoa.constant.RackGrade;
import com.lanoa.entity.QRackCode;
import com.lanoa.entity.RackCode;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class RackCodeRepositoryTest {

    @Autowired
    RackCodeRepository rackCodeRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("랙코드 저장 테스트")
    public void createRackCodeTest() {
        RackCode savedRackCode = rackCodeRepository.save(RackCode.builder()
                .rackCodeId("A010101")
                .rackGrade(RackGrade.NORMAL)
                .rackName("정상A랙")
                .build());

        System.out.println(savedRackCode.toString());
    }

    public void createRackCodeList() {
        for (int i=1; i<6; i++) {
            rackCodeRepository.save(RackCode.builder()
                    .rackCodeId("A01010" + i)
                    .rackGrade(RackGrade.NORMAL)
                    .rackName("정상A랙")
                    .build());
        }
    }

    @Test
    @DisplayName("랙코드 조회 테스트")
    public void selectRackCodeByRackCodeNum() {
        this.createRackCodeList();

        RackCode rackCode = rackCodeRepository.findByRackCodeId("A010101");

        System.out.println(rackCode.toString());
    }

    @Test
    @DisplayName("랙코드 조회 테스트")
    public void selectRackCode() {
        this.createRackCodeList();

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QRackCode qRackCode = QRackCode.rackCode;

        JPAQuery<RackCode> query = queryFactory.selectFrom(qRackCode)
                .where(qRackCode.rackCodeId.like("A01010" + "%"))
                .orderBy(qRackCode.rackCodeId.desc());

        List<RackCode> rackCodeList = query.fetch();

        for (RackCode rackCode : rackCodeList) {
            System.out.println(rackCode.toString());
        }
    }
}