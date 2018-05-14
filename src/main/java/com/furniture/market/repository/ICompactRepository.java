package com.furniture.market.repository;

import com.furniture.market.entity.Compact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Lijq
 */
@Repository
public interface ICompactRepository extends JpaRepository<Compact, Integer>, JpaSpecificationExecutor<Compact> {

    Compact getByNo(String no);

    Compact getById(int id);

    @Modifying
    @Query(value = "UPDATE Compact  SET rentOfReceived= :rentOfReceived, lastReceivedDate= :lastReceivedDate WHERE id= :id")
    void updateRentOfReceived(@Param("id") Integer id, @Param("rentOfReceived") BigDecimal rentOfReceived,
                              @Param("lastReceivedDate") Date lastReceivedDate);

    @Modifying
    @Query(value = "UPDATE Compact  SET isReturnPledge= :isReturnPledge, returnPledgeTime= :returnPledgeTime WHERE id= :id")
    void updateReturnPledge(@Param("id") Integer id, @Param("isReturnPledge") boolean isReturnPledge,
                            @Param("returnPledgeTime") Date returnPledgeTime);

}
