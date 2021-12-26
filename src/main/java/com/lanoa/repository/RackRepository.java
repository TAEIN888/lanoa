package com.lanoa.repository;

import com.lanoa.entity.Rack;
import com.lanoa.entity.RackId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RackRepository extends JpaRepository<Rack, RackId>, RackRepositoryCustom {

    //List<Rack> findByRackCode(String rackCode);

    Optional<Rack> findById(RackId rackId);
}
