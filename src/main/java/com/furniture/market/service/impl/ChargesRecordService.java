package com.furniture.market.service.impl;

import com.furniture.market.entity.ChargesRecord;
import com.furniture.market.entity.Compact;
import com.furniture.market.model.MiniPage;
import com.furniture.market.model.Pagination;
import com.furniture.market.repository.IChargesRecordRepository;
import com.furniture.market.service.IChargesRecordService;
import com.furniture.market.service.ICompactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;

/**
 * @author Lijq
 */
@Slf4j
@Service
public class ChargesRecordService implements IChargesRecordService {

    @Autowired
    private IChargesRecordRepository chargesRecordRepository;

    @Autowired
    private ICompactService compactService;

    @Override
    public MiniPage page(Pagination pagination, String compactId) {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        PageRequest pageable = PageRequest.of(pagination.getPage(), pagination.getSize(), sort);

        //通常使用 Specification 的匿名内部类
        Specification<ChargesRecord> specification = (Specification<ChargesRecord>) (root, query, cb) -> {
            Path path = root.get("compact");
            Predicate predicate = cb.equal(path.get("id"), compactId);
            return predicate;
        };

        Page<ChargesRecord> page = chargesRecordRepository.findAll(specification, pageable);

        MiniPage mini = new MiniPage();
        mini.setData(page.getContent());
        mini.setTotal(page.getTotalElements());
        return mini;
    }


    @Transactional
    @Override
    public ChargesRecord save(ChargesRecord chargesRecord, Integer compactId) {

        Compact compact = compactService.getById(compactId);

        if (compact == null) {
            return null;
        }

        // 修改已收款
        BigDecimal rentOfReceived = chargesRecord.getRent();
        compactService.updateRentOfAriReceived(compact.getId(), rentOfReceived);

        chargesRecord.setCompact(compact);
        chargesRecord = chargesRecordRepository.save(chargesRecord);
        return chargesRecord;
    }
}
