package com.furniture.market.security;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 权限中，当前用户信息
 *
 * @author Lijq
 */
@Data
public class Account implements Serializable {

    private Integer id;
    private String username;
    private String name;
    private String phone;
    private Date createTime;
}
