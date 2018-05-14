package com.furniture.market.service;

import com.furniture.market.entity.Admin;

/**
 * @author Lijq
 */
public interface IAdminService {

    Admin get(int id);

    Admin get(String username);

    Admin updatePassword(String password);

    Admin updatePhone(String phone);


}
