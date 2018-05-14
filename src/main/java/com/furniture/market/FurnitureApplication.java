package com.furniture.market;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * 启动类，部署以war包形式
 *
 * @author Lijq
 * EnableJpaAuditing 允许在操作时自动保存操作人、时间
 */
@SpringBootApplication
@EnableJpaAuditing
public class FurnitureApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(FurnitureApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(FurnitureApplication.class, args);
    }
}
