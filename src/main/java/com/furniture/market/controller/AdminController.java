package com.furniture.market.controller;

import com.furniture.market.entity.Admin;
import com.furniture.market.security.Account;
import com.furniture.market.security.ShiroManager;
import com.furniture.market.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * Admin 控制器
 *
 * @author Lijq
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private IAdminService adminService;


    @GetMapping(value = "/info")
    public String info(ModelMap model) {
        return "/admin/info";
    }

    @PostMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<Account> admin(@PathVariable(name = "id") Integer id, ModelMap model) {

        Account account = new Account();

        if (id == 0) {
            id = ShiroManager.getAccount().getId();
        }

        Admin admin = adminService.get(id);
        if (admin != null) {
            account.setId(admin.getId());
            account.setUsername(admin.getUsername());
            account.setName(admin.getName());
            account.setPhone(admin.getPhone());
            account.setCreateTime(admin.getCreateTime());
            return new ResponseEntity<>(account, HttpStatus.OK);
        }
        return new ResponseEntity<>(account, HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
