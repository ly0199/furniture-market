package com.furniture.market.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 空调、质检、垃圾
 *
 * @author Lijq
 */
@Setter
@Getter
@Entity
@Table(name = "t_charges_record")
@EntityListeners(AuditingEntityListener.class)
public class ChargesRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "compact_id", referencedColumnName = "id")
    @JsonIgnoreProperties("rentRecords")
    private Compact compact;

    /**
     * 计费开始时间
     */
    @Temporal(TemporalType.DATE)
    private Date startDate;

    /**
     * 计费结束时间
     */
    @Temporal(TemporalType.DATE)
    private Date endDate;

    /**
     * 月数
     */
    @Column
    private int month;

    /**
     * 空调、质检、垃圾处理费用
     */
    @Column
    private BigDecimal rent;

    @Column
    private String remark;

    /**
     * 操作相关，自动生成
     */
    @CreatedDate
    private Date createTime;
    @CreatedBy
    private String creator;
    @LastModifiedDate
    private Date updateTime;
    @LastModifiedBy
    private String updater;
}
