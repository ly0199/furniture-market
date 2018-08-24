package com.furniture.market.repository;

import com.furniture.market.entity.ChargesRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Lijq
 */
@Repository
public interface IChargesRecordRepository extends JpaRepository<ChargesRecord, Integer>, JpaSpecificationExecutor<ChargesRecord> {

    List<ChargesRecord> findByCompactId(Integer compactId);
}
