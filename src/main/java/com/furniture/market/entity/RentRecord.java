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
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 租金实体类
 *
 * @author Lijq
 */
@Getter
@Setter
@Entity
@Table(name = "t_rent_record")
@EntityListeners(AuditingEntityListener.class)
public class RentRecord implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "compact_id", referencedColumnName = "id")
    @JsonIgnoreProperties("rentRecords")
    private Compact compact;

    /**
     * 租金
     */
    @Column
    private BigDecimal rent;

    /**
     * 月数
     */
    @Column
    private int month;

    /**
     * 租金开始日期
     */
    @Temporal(TemporalType.DATE)
    private Date startDate;

    /**
     * 租金结束日期
     */
    @Temporal(TemporalType.DATE)
    private Date endDate;

    /**
     * 备注
     */
    @Column
    private String remark;

    /**
     * 操作相关
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
