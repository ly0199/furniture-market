package com.furniture.market.entity;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Lijq
 * @date 2018/4/14 20:59
 * @description Compact
 */
@Getter
@Setter
@Entity
@Table(name = "t_compact")
@EntityListeners(AuditingEntityListener.class)
public class Compact implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 基础信息
     * 合同号| 商户名称| 承租人名| 合同开始时间| 合同结束时间| 合同周期数
     */
    @Column(name = "no", nullable = false, length = 50)
    private String no;

    @Column(name = "merchant", nullable = false, length = 50)
    private String merchant;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Temporal(TemporalType.DATE)
    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Column(name = "count_of_month", nullable = false)
    private Integer countOfMonth;

    /**
     * 摊位号| 面积大小| 支付方式| 折扣|
     */
    @Column(name = "booth", nullable = false, length = 50)
    private String booth;

    @Column(precision = 19, scale = 2, nullable = false)
    private BigDecimal area;

    @Column(name = "getway", nullable = false)
    private Integer getway;

    @Column(precision = 19, scale = 2, nullable = false)
    private BigDecimal discount;


    /**
     * 租金（平米/月）| 管理费（平米/月）| 杂费[空调,质检,垃圾处理] (平米/月)
     */
    @Column(precision = 19, scale = 2, nullable = false)
    private BigDecimal rentOfArea;
    @Column(precision = 19, scale = 2, nullable = false)
    private BigDecimal rentOfManage;
    @Column(precision = 19, scale = 2, nullable = false)
    private BigDecimal rentOfCharges;

    /**
     * 月租金 = 面积 * 租金（平米/月）* 折扣
     * 月管理费 = 面积 * 管理费（平米/月） * 折扣
     * 月杂费（空调、质检、垃圾处理） = 面积 * 杂费[空调,质检,垃圾处理] (平米/月)
     */
    @Column(precision = 19, scale = 2, nullable = false)
    private BigDecimal rentOfAreaMonth;
    @Column(precision = 19, scale = 2, nullable = false)
    private BigDecimal rentOfManageMonth;
    @Column(precision = 19, scale = 2, nullable = false)
    private BigDecimal rentOfChargesMonth;

    /**
     * 月合计 = 月租金 + 月管理费 + 月杂费
     * 合同期合计 = 月合计 * 合同周期数
     * 减免优惠
     * 应收合计 = 合同期合计 - 减免优惠
     */
    @Column(precision = 19, scale = 2, nullable = false)
    private BigDecimal rentOfMonthTotal;
    @Column(precision = 19, scale = 2, nullable = false)
    private BigDecimal rentOfCompact;
    @Column(precision = 19, scale = 2, nullable = false)
    private BigDecimal rentOfDiscounts;
    @Column(precision = 19, scale = 2, nullable = false)
    private BigDecimal rentOfReceivable;

    /**
     * 已收合计 = 每次收取租金之和
     */
    @Column(precision = 19, scale = 2, nullable = false)
    private BigDecimal rentOfReceived;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastReceivedDate;


    /**
     * 押金、是否退还、退还时间、押金备注
     */
    @Column(precision = 19, scale = 2, nullable = false)
    private BigDecimal rentOfPledge;
    private Boolean isReturnPledge;
    @Temporal(TemporalType.TIMESTAMP)
    private Date returnPledgeTime;
    private String remark;

    /**
     * 操作相关信息
     */
    @CreatedDate
    private Date createTime;
    @CreatedBy
    private String creater;
    @LastModifiedDate
    private Date updateTime;
    @LastModifiedBy
    private String updater;

    /**
     * 是否可以用
     */
    @Column(name = "enabled", columnDefinition = "char default 1")
    private Boolean enabled;


    /**
     * 欠收
     */
    public BigDecimal getRentOfDebt() {
        return this.rentOfReceivable.subtract(this.rentOfReceived);
    }

    /**
     * 租金最后日期
     */
    public String getLastReceivedDateStr() {
        if (this.lastReceivedDate != null) {
            return DateFormatUtils.format(this.lastReceivedDate, "yyyy-MM-dd");
        }
        return "";
    }

    /**
     * 滞纳金月数
     */
    public int getCountOfLate() {
        Date date1 = new Date();
        Date date2 = lastReceivedDate;
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        int count = (calendar2.get(Calendar.MONTH) - calendar1.get(Calendar.MONTH)) >= 0 ?
                (calendar2.get(Calendar.MONTH) - calendar1.get(Calendar.MONTH)) :
                Math.abs((calendar2.get(Calendar.MONTH) - calendar1.get(Calendar.MONTH))) + 1;
        count += Math.abs((calendar2.get(Calendar.YEAR) - calendar1.get(Calendar.YEAR)) * 12);
        return count;
    }

    /**
     * 滞纳金
     */
    public BigDecimal getLate() {
        return this.rentOfMonthTotal.multiply(new BigDecimal(0.003)).multiply(new BigDecimal(getCountOfLate()));
    }
}