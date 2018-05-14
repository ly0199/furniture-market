package com.furniture.market.repository;

import com.furniture.market.entity.RentRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Lijq
 */
@Repository
public interface IRentRecordRepository extends JpaRepository<RentRecord, Integer>, JpaSpecificationExecutor<RentRecord> {

    List<RentRecord> findByCompactId(Integer compactId);
}
