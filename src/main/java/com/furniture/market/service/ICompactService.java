package com.furniture.market.service;

import com.furniture.market.entity.Compact;
import com.furniture.market.model.MiniPage;
import com.furniture.market.model.Pagination;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Lijq
 */
public interface ICompactService {

    MiniPage page(Pagination pagination);

    Compact save(Compact compact);

    Compact getById(int id);

    Compact getByNo(String no);

    MiniPage search(Pagination pagination, String keywords);

    void updateRentOfReceived(Integer id, BigDecimal add, Date lastReceivedDate);

    void updateReturnPledge(Integer compactId);

    MiniPage expirePage(Pagination pagination);
}
