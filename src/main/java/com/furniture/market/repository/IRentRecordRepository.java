package com.furniture.market.repository;

import com.furniture.market.entity.RentRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Lijq
 * @date 2018/4/25 11:14
 * @description
 */
@Repository
public interface IRentRecordRepository extends JpaRepository<RentRecord, Integer> {
    List<RentRecord> findByCompactId(Integer compactId);
}
