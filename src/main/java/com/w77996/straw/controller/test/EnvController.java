package com.w77996.straw.controller.test;

import com.w77996.straw.core.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 环境测试
 * @author: straw
 **/
@RestController
@Slf4j
@RequestMapping("/env")
public class EnvController {

    @Value("${spring.profiles.active}")
    private String env;

    @GetMapping
    public Result env() {
        return Result.success(env);
    }
}
