package com.furniture.market.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

/**
 * Druid源控制类
 *
 * @author Lijq
 */
@Configuration
@ConditionalOnClass(value = DruidDataSource.class)
public class DruidDataSourceConfiguration {
}
