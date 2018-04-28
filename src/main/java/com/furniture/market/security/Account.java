package com.furniture.market.security;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Lijq
 * @date 2018/4/10 11:01
 * @description
 */
@Data
public class Account implements Serializable {

    private Integer id;
    private String username;
    private String name;
    private String phone;
    private Date createTime;

}
