package com.zly.skeleton.biz.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ZLY
 * @since 2026/10/20 11:47
 */
@RestController
@RequestMapping("/test/login")
public class TestController
{

    @GetMapping("/test-01")
    public String test() {
        return "success";
    }

}
