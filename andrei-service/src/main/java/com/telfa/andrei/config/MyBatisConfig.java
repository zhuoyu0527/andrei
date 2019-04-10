package com.telfa.andrei.config;

import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * MyBatis配置类
 * @since 1.8
 */
@Configuration
@MapperScan(basePackages = { "com.telfa.andrei.mapper" })
public class MyBatisConfig {
}
