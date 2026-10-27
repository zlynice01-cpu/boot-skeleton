package com.zly.skeleton.biz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author ZLY
 * @since 2026/10/20 11:24
 */
@MapperScan(basePackages = {"com.zly.skeleton.biz.mapper", "com.zly.skeleton.common.security.mapper"})
@ComponentScan(basePackages = "com.zly.skeleton.**")
@EnableMethodSecurity
@SpringBootApplication
public class ApplicationStarter
{

    public static void main(String[] args)
    {
        SpringApplication.run(ApplicationStarter.class);
    }

}
