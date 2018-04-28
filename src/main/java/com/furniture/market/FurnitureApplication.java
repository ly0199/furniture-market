package com.furniture.market;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author Lijq
 * @date 2018/4/10 9:00
 * @description
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
