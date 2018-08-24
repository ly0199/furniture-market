package com.furniture.market.entity;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
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
 * 合同实体类
 *
 * @author Lijq
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
     * 合同号
     */
    @Column(name = "no", nullable = false, length = 50)
    private String no;

    /**
     * 商户名称
     */
    @Column(name = "merchant", nullable = false, length = 50)
    private String merchant;

    /**
     * 承租人名
     */
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    /**
     * 合同开始时间
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "start_date", nullable = false)
    private Date startDate;

    /**
     * 合同结束时间
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "end_date", nullable = false)
    private Date endDate;

    /**
     * 合同周期数
     */
    @Column(name = "count_of_month", nullable = false)
    private Integer countOfMonth;

    /**
     * 摊位号
     */
    @Column(name = "booth", nullable = false, length = 50)
    private String booth;

    /**
     * 面积大小
     */
    @Column(precision = 19, scale = 2, nullable = false)
    private BigDecimal area;

    /**
     * 支付方式
     */
    @Column(name = "getway", nullable = false)
    private Integer getway;

    /**
     * 折扣: 根据支付方式选取折扣率 0.00-100.00
     */
    @Column(precision = 19, scale = 2, nullable = false)
    private BigDecimal discount;


    //---------- 单位面积每月租金计收单价 -----------------------------------------------------------//

    /**
     * 租金（平米/月）
     */
    @Column(precision = 19, scale = 2, nullable = false)
    private BigDecimal rentOfArea;

    /**
     * 管理费（平米/月
     */
    @Column(precision = 19, scale = 2, nullable = false)
    private BigDecimal rentOfManage;

    /**
     * 杂费[空调,质检,垃圾处理] (平米/月)
     */
    @Column(precision = 19, scale = 2, nullable = false)
    private BigDecimal rentOfCharges;


    //---------- 每月租金计收合计 -----------------------------------------------------------//
    /**
     * 月租金 = 面积 * 租金（平米/月）* 折扣
     */
    @Column(precision = 19, scale = 2, nullable = false)
    private BigDecimal rentOfAreaMonth;
    /**
     * 月管理费 = 面积 * 管理费（平米/月） * 折扣
     */
    @Column(precision = 19, scale = 2, nullable = false)
    private BigDecimal rentOfManageMonth;
    /**
     * 月杂费（空调、质检、垃圾处理） = 面积 * 杂费[空调,质检,垃圾处理] (平米/月)
     */
    @Column(precision = 19, scale = 2, nullable = false)
    private BigDecimal rentOfChargesMonth;
    /**
     * 月合计 = 月租金 + 月管理费 + 月杂费
     */
    @Column(precision = 19, scale = 2, nullable = false)
    private BigDecimal rentOfMonthTotal;
    /**
     * 合同期合计 = 月合计 * 合同周期数
     */
    @Column(precision = 19, scale = 2, nullable = false)
    private BigDecimal rentOfCompact;
    /**
     * 减免优惠
     */
    @Column(precision = 19, scale = 2, nullable = false)
    private BigDecimal rentOfDiscounts;
    /**
     * 应收合计 = 合同期合计 - 减免优惠
     */
    @Column(precision = 19, scale = 2, nullable = false)
    private BigDecimal rentOfReceivable;

    /**
     * 已收合计 = 每次收取租金之和
     */
    @Column(precision = 19, scale = 2, nullable = false)
    private BigDecimal rentOfReceived;

    /**
     * 最后收款日
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastReceivedDate;


    /**
     * 合同保证金、是否退还、退还时间、合同保证金备注
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
    private String creator;
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
     * 滞纳金天数
     * 不能修改成 private
     *
     * @return int
     */
    public long getCountOfLate() {

        // 当没收过租金时，不计算滞纳金
        if (lastReceivedDate == null) {
            return 0;
        }

        // 计算滞纳金
        // 当前日期
        Date date = new Date();
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date);
        calendar1.set(Calendar.HOUR_OF_DAY, 0);
        calendar1.set(Calendar.MINUTE, 0);
        calendar1.set(Calendar.SECOND, 0);

        // 租金最后日期
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(lastReceivedDate);
        calendar2.set(Calendar.HOUR_OF_DAY, 0);
        calendar2.set(Calendar.MINUTE, 0);
        calendar2.set(Calendar.SECOND, 0);

        if (calendar1.getTimeInMillis() <= calendar2.getTimeInMillis()) {
            return 0;
        }

        long millis = calendar1.getTimeInMillis() - calendar2.getTimeInMillis();


        return millis % DateUtils.MILLIS_PER_DAY == 0 ?
                millis / DateUtils.MILLIS_PER_DAY : millis / DateUtils.MILLIS_PER_DAY + 1;
    }

    /**
     * 滞纳金
     */
    public BigDecimal getLate() {
        return this.rentOfAreaMonth.add(rentOfManageMonth).multiply(new BigDecimal(0.003)).multiply(new BigDecimal(getCountOfLate()));
    }
}