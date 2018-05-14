package com.furniture.market.repository;

import com.furniture.market.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Lijq
 */
@Repository
public interface IAdminRepository extends JpaRepository<Admin, Integer> {

    Admin getByUsername(String username);
}
