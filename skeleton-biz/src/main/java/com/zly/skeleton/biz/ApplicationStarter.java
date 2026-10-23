package com.zly.skeleton.biz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author ZLY
 * @since 2026/10/20 11:24
 */
@MapperScan(basePackages = "com.zly.login.biz.mapper")
@ComponentScan(basePackages = "com.zly.login.**")
@SpringBootApplication
public class ApplicationStarter
{

    public static void main(String[] args)
    {
        SpringApplication.run(ApplicationStarter.class);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pwd = encoder.encode("123456");
        System.out.println(pwd);
    }

}
