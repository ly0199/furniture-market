package com.furniture.market.service.impl;

import com.furniture.market.entity.Compact;
import com.furniture.market.entity.RentRecord;
import com.furniture.market.model.MiniPage;
import com.furniture.market.model.Pagination;
import com.furniture.market.repository.IRentRecordRepository;
import com.furniture.market.service.ICompactService;
import com.furniture.market.service.IRentRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Lijq
 * @date 2018/4/25 11:11
 * @description
 */
@Service
public class RentRecordServiceImpl implements IRentRecordService {

    @Autowired
    private IRentRecordRepository rentRecordRepository;

    @Autowired
    private ICompactService compactService;

    @Override
    public MiniPage page(Pagination pagination, String compactId) {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Page<RentRecord> page = rentRecordRepository.findAll(PageRequest.of(pagination.getPage(), pagination.getSize(), sort));
        MiniPage mini = new MiniPage();
        mini.setData(page.getContent());
        mini.setTotal(page.getTotalElements());
        return mini;
    }


    @Transactional()
    @Override
    public RentRecord save(RentRecord rentRecord, Integer compactId) {

        Compact compact = compactService.getById(compactId);

        if (compact == null) {
            return null;
        }

        // 修改已收款
        BigDecimal rentOfReceived = rentRecord.getRent();
        List<RentRecord> list = rentRecordRepository.findByCompactId(compactId);
        for (RentRecord r : list) {
            rentOfReceived = rentOfReceived.add(r.getRent());
        }

        compactService.updateRentOfReceived(compact.getId(), rentOfReceived, rentRecord.getEndDate());

        rentRecord.setCompact(compact);
        rentRecord = rentRecordRepository.save(rentRecord);

        return rentRecord;
    }
}
