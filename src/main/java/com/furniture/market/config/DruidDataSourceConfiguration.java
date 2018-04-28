package com.furniture.market.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

/**
 * @author Lijq
 * @date 2018/4/18 15:46
 * @description
 */
@Configuration
@ConditionalOnClass(value = DruidDataSource.class)
public class DruidDataSourceConfiguration {
}
