package com.furniture.market.util;

import java.math.BigDecimal;

/**
 * @author Lijq
 * @date 2018/4/20 13:39
 * @description 租金工具类
 */
public class RentUtil {

    public static final int PAY_1 = 1;
    public static final int PAY_6 = 6;
    public static final int PAY_12 = 12;
    public static final int PAY_18 = 18;

    /**
     * 根据缴费方式获取折扣率
     *
     * @param getway 缴费方式
     * @return
     */
    public static BigDecimal getDiscount(int getway) {
        if (getway == PAY_6) {
            return new BigDecimal(0.95);
        } else if (getway == PAY_12) {
            return new BigDecimal(0.90);
        } else if (getway == PAY_18) {
            return new BigDecimal(0.88);
        } else {
            return new BigDecimal(1);
        }
    }


    /**
     * 获取 每月面积租金合计
     *
     * @param rentOfArea 平米/月 租金
     * @param area       面积
     * @param discount   折扣
     * @return
     */
    public static BigDecimal getRentOfAreaMonth(BigDecimal rentOfArea, BigDecimal area, BigDecimal discount) {
        return rentOfArea.multiply(area).multiply(discount);
    }

    /**
     * 获取 每月管理费合计
     *
     * @param rentOfManage 平米/月 管理费
     * @param area         面积
     * @param discount     折扣
     * @return
     */
    public static BigDecimal getRentOfManageMonth(BigDecimal rentOfManage, BigDecimal area, BigDecimal discount) {
        return rentOfManage.multiply(area).multiply(discount);
    }

    /**
     * 获取 每月空调费/质检费/垃圾处理费 合计
     *
     * @param rentOfCharges 平米/月 杂费
     * @param area          面积
     * @return
     */
    public static BigDecimal getRentOfChargesMonth(BigDecimal rentOfCharges, BigDecimal area) {
        return rentOfCharges.multiply(area);
    }


    /**
     * 获取 每月租金合计
     *
     * @param rentOfAreaMonth    每月面积租金
     * @param rentOfmanageMonth  每月管理费租金
     * @param rentOfChargesMonth 每月杂费租金
     * @return
     */
    public static BigDecimal getRentOfMonthTotal(BigDecimal rentOfAreaMonth, BigDecimal rentOfmanageMonth,
                                                 BigDecimal rentOfChargesMonth) {
        return rentOfAreaMonth.add(rentOfmanageMonth).add(rentOfChargesMonth);
    }

    /**
     * 获取 合同期总租金
     *
     * @param rentOfMonthTotal 每月租金合计
     * @param countOfMonth     合同期月个数
     * @param rentOfDiscounts  减免优惠额度
     * @return
     */
    public static BigDecimal getRentOfCompact(BigDecimal rentOfMonthTotal, int countOfMonth, BigDecimal rentOfDiscounts) {
        return rentOfMonthTotal.multiply(new BigDecimal(countOfMonth)).subtract(rentOfDiscounts);
    }


    /**
     * 保证金
     *
     * @param rentOfMonthTotal 每个月租金合计
     * @return
     */
    public static BigDecimal getPledge(BigDecimal rentOfMonthTotal) {
        return rentOfMonthTotal.multiply(new BigDecimal(3));
    }


    public static void main(String[] args) {
        System.out.println(getRentOfAreaMonth(new BigDecimal(10), new BigDecimal(500), new BigDecimal(0.9)));
    }


}
