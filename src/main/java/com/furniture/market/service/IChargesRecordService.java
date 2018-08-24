package com.furniture.market.service;

import com.furniture.market.entity.ChargesRecord;
import com.furniture.market.model.MiniPage;
import com.furniture.market.model.Pagination;

/**
 * @author Lijq
 */
public interface IChargesRecordService {

    MiniPage page(Pagination pagination, String compactId);

    ChargesRecord save(ChargesRecord chargesRecord, Integer compactId);
}
