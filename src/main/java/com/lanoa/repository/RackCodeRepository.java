package com.lanoa.repository;

import com.lanoa.entity.RackCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RackCodeRepository extends JpaRepository<RackCode, Long>, RackCodeRepositoryCustom {

    RackCode findByRackCodeId(String rackCodeId);
}
