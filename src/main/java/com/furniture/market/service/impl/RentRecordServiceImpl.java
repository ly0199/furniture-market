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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * 租金业务实现类
 *
 * @author Lijq
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
        PageRequest pageable = PageRequest.of(pagination.getPage(), pagination.getSize(), sort);

        //通常使用 Specification 的匿名内部类
        Specification<RentRecord> specification = new Specification<RentRecord>() {
            /**
             * @param *root: 代表查询的实体类.
             * @param query: 可以从中可到 Root 对象, 即告知 JPA Criteria 查询要查询哪一个实体类. 还可以
             * 来添加查询条件, 还可以结合 EntityManager 对象得到最终查询的 TypedQuery 对象.
             * @param *cb: CriteriaBuilder 对象. 用于创建 Criteria 相关对象的工厂. 当然可以从中获取到 Predicate 对象
             * @return: *Predicate 类型, 代表一个查询条件.
             */
            @Override
            public Predicate toPredicate(Root<RentRecord> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {

                Path path = root.get("compact");
                Predicate predicate = cb.equal(path.get("id"), compactId);
                return predicate;
            }
        };


        Page<RentRecord> page = rentRecordRepository.findAll(specification, pageable);

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
        compactService.updateRentOfReceived(compact.getId(), rentOfReceived, rentRecord.getEndDate());

        rentRecord.setCompact(compact);
        rentRecord = rentRecordRepository.save(rentRecord);

        return rentRecord;
    }
}
