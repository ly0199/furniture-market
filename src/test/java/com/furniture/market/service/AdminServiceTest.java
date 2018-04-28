package com.furniture.market.service;

import com.furniture.market.FurnitureApplication;
import com.furniture.market.entity.Admin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Lijq
 * @date 2018/4/14 22:51
 * @description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = FurnitureApplication.class)
public class AdminServiceTest {

    @Autowired
    private IAdminService adminService;


    @Test
    public void getByUsername() {
        String username = "admin";
        Admin admin = adminService.get(username);
        System.out.println(admin);
    }
}
