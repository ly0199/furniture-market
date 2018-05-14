package com.furniture.market.service;

import com.furniture.market.entity.RentRecord;
import com.furniture.market.model.MiniPage;
import com.furniture.market.model.Pagination;

/**
 * @author Lijq
 */
public interface IRentRecordService {

    MiniPage page(Pagination pagination, String compactId);

    RentRecord save(RentRecord rentRecord, Integer compactId);
}
