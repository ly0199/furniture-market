package com.furniture.market.repository;

import com.furniture.market.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Lijq
 * @date 2018/4/18 15:44
 * @description
 */
@Repository
public interface IAdminRepository extends JpaRepository<Admin, Integer> {

    Admin getByUsername(String username);
}
