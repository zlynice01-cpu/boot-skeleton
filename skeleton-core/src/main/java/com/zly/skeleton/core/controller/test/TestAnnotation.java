package com.zly.skeleton.core.controller.test;

import com.zly.skeleton.common.web.annotation.OperLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ZLY
 * @since 2026/11/11 9:42
 */
@Slf4j
@RestController
@RequestMapping("/test/annotation")
@RequiredArgsConstructor
public class TestAnnotation
{
    @OperLog(title = "测试注解")
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
