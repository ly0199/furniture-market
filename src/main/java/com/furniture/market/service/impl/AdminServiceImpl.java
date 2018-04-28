package com.furniture.market.service.impl;

import com.furniture.market.entity.Admin;
import com.furniture.market.repository.IAdminRepository;
import com.furniture.market.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Lijq
 * @date 2018/4/14 21:02
 * @description
 */
@Service
public class AdminServiceImpl implements IAdminService{

    @Autowired
    private IAdminRepository adminRepository;

    @Override
    public Admin get(int id) {
        return adminRepository.getOne(id);
    }

    @Override
    public Admin get(String username) {
        return adminRepository.getByUsername(username);
    }

    @Override
    public Admin updatePassword(String password) {
        return null;
    }

    @Override
    public Admin updatePhone(String phone) {
        return null;
    }
}
