package com.lanoa.repository;

import com.lanoa.entity.RackCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RackCodeRepository extends JpaRepository<RackCode, Long>, RackCodeRepositoryCustom {

    List<RackCode> findByRackCodeId(String rackCode);
}
