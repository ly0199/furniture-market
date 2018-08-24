package com.furniture.market.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Admin 实体类
 *
 * @author Lijq
 */
@Setter
@Getter
@Entity
@Table(name = "t_admin")
public class Admin implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username", unique = true, length = 50, nullable = false)
    private String username;

    @Column(name = "password", length = 50, nullable = false)
    private String password;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "phone", length = 50, nullable = false)
    private String phone;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time", length = 50, nullable = false)
    private Date createTime;

    @Column(name = "enabled", columnDefinition = "char default 1")
    private Boolean enabled;
}