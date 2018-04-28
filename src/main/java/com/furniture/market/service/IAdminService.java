package com.furniture.market.service;

import com.furniture.market.entity.Admin;

/**
 * @author Lijq
 * @date 2018/4/14 20:50
 * @description
 */
public interface IAdminService {

    Admin get(int id);

    Admin get(String username);

    Admin updatePassword(String password);

    Admin updatePhone(String phone);


}
