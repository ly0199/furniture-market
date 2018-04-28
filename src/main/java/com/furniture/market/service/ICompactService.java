package com.furniture.market.service;

import com.furniture.market.entity.Compact;
import com.furniture.market.model.MiniPage;
import com.furniture.market.model.Pagination;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Lijq
 * @date 2018/4/18 9:34
 * @description
 */
public interface ICompactService {

    MiniPage page(Pagination pagination);

    Compact save(Compact compact);

    /**
     * 根据id获取 compact
     *
     * @param id id
     * @return Compact
     */
    Compact getById(int id);


    Compact getByNo(String no);

    MiniPage search(Pagination pagination, String keywords);

    void updateRentOfReceived(Integer id, BigDecimal add, Date lastReceivedDate);

    void updateReturnPledge(Integer compactId);

    MiniPage expirePage(Pagination pagination);
}
