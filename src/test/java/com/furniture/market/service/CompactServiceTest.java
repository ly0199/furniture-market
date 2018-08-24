package com.furniture.market.service;

import com.alibaba.fastjson.JSON;
import com.furniture.market.FurnitureApplication;
import com.furniture.market.entity.Compact;
import com.furniture.market.model.MiniPage;
import com.furniture.market.model.Pagination;
import com.furniture.market.util.RentUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author Lijq
 * @date 2018/4/20 9:20
 * @description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = FurnitureApplication.class)
public class CompactServiceTest {

    @Autowired
    private ICompactService compactService;

    @Test
    public void page() {

        Pagination pagination = new Pagination();
        pagination.setPage(0);
        pagination.setSize(20);
        MiniPage page = compactService.page(pagination, "");
        page.getData().forEach(System.out::println);
    }

    @Test
    @Transactional
    public void batchInsert() throws Exception {
        for (int i = 1; i < 50; i++) {
            add("00" + i);
        }
    }


    private void add(String uun) throws Exception {

        Compact compact = new Compact();

        compact.setNo("测试合同" + uun);
        compact.setMerchant("测试商家" + uun);
        compact.setName("测试租户" + uun);
        compact.setStartDate(DateUtils.parseDate("2018-04-20", "yyyy-MM-dd"));
        compact.setEndDate(DateUtils.parseDate("2019-04-20", "yyyy-MM-dd"));
        compact.setCountOfMonth(12);


        compact.setBooth("测试摊位号" + uun);
        compact.setArea(new BigDecimal(544.33).setScale(2, RoundingMode.HALF_UP));
        compact.setGetway(RentUtil.PAY_1);
        compact.setDiscount(RentUtil.getDiscount(compact.getGetway()));

        compact.setRentOfArea(new BigDecimal(10));
        compact.setRentOfManage(new BigDecimal(20));
        compact.setRentOfCharges(new BigDecimal(5.8));

        compact.setRentOfAreaMonth(RentUtil.getRentOfAreaMonth(compact.getRentOfArea(), compact.getArea(), compact.getDiscount()));
        compact.setRentOfManageMonth(RentUtil.getRentOfManageMonth(compact.getRentOfManage(), compact.getArea(), compact.getDiscount()));
        compact.setRentOfChargesMonth(RentUtil.getRentOfChargesMonth(compact.getRentOfCharges(), compact.getArea()));

        compact.setRentOfDiscounts(new BigDecimal(4000));

        compact.setRentOfMonthTotal(RentUtil.getRentOfMonthTotal(compact.getRentOfAreaMonth(),
                compact.getRentOfManageMonth(), compact.getRentOfChargesMonth()));

        compact.setRentOfCompact(RentUtil.getRentOfCompact(compact.getRentOfMonthTotal(), compact.getCountOfMonth(),
                compact.getRentOfDiscounts()));

        compact.setEnabled(true);

        compactService.save(compact);
    }


    @Test
    @Transactional
    public void query() {
        Compact compact = compactService.getById(4);
        System.out.println(JSON.toJSONString(compact));
    }


}
