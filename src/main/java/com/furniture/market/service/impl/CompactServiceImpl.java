package com.furniture.market.service.impl;

import com.furniture.market.entity.Compact;
import com.furniture.market.model.MiniPage;
import com.furniture.market.model.Pagination;
import com.furniture.market.repository.ICompactRepository;
import com.furniture.market.service.ICompactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Lijq
 * @date 2018/4/18 9:34
 * @description
 */
@Slf4j
@Service
public class CompactServiceImpl implements ICompactService {

    @Autowired
    private ICompactRepository compactRepository;

    @Override
    public MiniPage page(Pagination pagination) {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Page<Compact> page = compactRepository.findAll(PageRequest.of(pagination.getPage(), pagination.getSize(), sort));
        MiniPage mini = new MiniPage();
        mini.setData(page.getContent());
        mini.setTotal(page.getTotalElements());
        return mini;
    }

    @Override
    public Compact save(Compact compact) {
        return compactRepository.save(compact);
    }

    @Override
    public Compact getById(int id) {
        return compactRepository.getById(id);
    }

    @Override
    public Compact getByNo(String no) {
        return compactRepository.getByNo(no);
    }


    @Override
    public MiniPage search(Pagination pagination, String keywords) {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");

        // 合同号/商家名称/承租人/摊铺号
        Compact compact = new Compact();
        compact.setNo(keywords);
        compact.setMerchant(keywords);
        compact.setName(keywords);
        compact.setBooth(keywords);


        Page<Compact> page = compactRepository.findAll(Example.of(compact,
                ExampleMatcher.matchingAny().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)),
                PageRequest.of(pagination.getPage(), pagination.getSize(), sort));
        MiniPage mini = new MiniPage();
        mini.setData(page.getContent());
        mini.setTotal(page.getTotalElements());
        return mini;
    }

    @Override
    public void updateRentOfReceived(Integer id, BigDecimal rentOfReceived, Date lastReceivedDate) {
        compactRepository.updateRentOfReceived(id, rentOfReceived, lastReceivedDate);
    }

    @Override
    @Transactional
    public void updateReturnPledge(Integer compactId) {
        compactRepository.updateReturnPledge(compactId, true, new Date());
    }


    @Override
    @SuppressWarnings("unchecked")
    public MiniPage expirePage(Pagination pagination) {


        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        PageRequest pageable = PageRequest.of(pagination.getPage(), pagination.getSize(), sort);

        //通常使用 Specification 的匿名内部类
        Specification<Compact> specification = new Specification<Compact>() {
            /**
             * @param *root: 代表查询的实体类.
             * @param query: 可以从中可到 Root 对象, 即告知 JPA Criteria 查询要查询哪一个实体类. 还可以
             * 来添加查询条件, 还可以结合 EntityManager 对象得到最终查询的 TypedQuery 对象.
             * @param *cb: CriteriaBuilder 对象. 用于创建 Criteria 相关对象的工厂. 当然可以从中获取到 Predicate 对象
             * @return: *Predicate 类型, 代表一个查询条件.
             */
            @Override
            public Predicate toPredicate(Root<Compact> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {

                Path lastReceivedDatePath = root.<Date>get("lastReceivedDate");
                Path endDatePath = root.<Date>get("endDate");


                // 获取节点时间点
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
                calendar.add(Calendar.DATE, 15);
                calendar.set(Calendar.HOUR, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);

                Predicate predicate = cb.and(
                        cb.equal(root.get("getway"), 1),
                        cb.greaterThan(endDatePath, new Date()),
                        cb.lessThan(lastReceivedDatePath, calendar.getTime())
                );

                return predicate;
            }
        };

        Page<Compact> page = compactRepository.findAll(specification, pageable);

        MiniPage mini = new MiniPage();
        mini.setData(page.getContent());
        mini.setTotal(page.getTotalElements());
        return mini;
    }

}
