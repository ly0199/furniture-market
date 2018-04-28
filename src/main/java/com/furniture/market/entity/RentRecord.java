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
 * @author Lijq
 * @date 2018/4/19 15:06
 * @description
 */
@Getter
@Setter
@Entity
@Table(name = "t_compact_rent_record")
@EntityListeners(AuditingEntityListener.class)
public class RentRecord implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "compact_id", referencedColumnName = "id")
    @JsonIgnoreProperties("rentRecords")
    private Compact compact;

    private Integer type = 1;
    private BigDecimal rent;
    private String remark;

    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @CreatedDate
    private Date createTime;
    @CreatedBy
    private String creater;
    @LastModifiedDate
    private Date updateTime;
    @LastModifiedBy
    private String updater;
}
