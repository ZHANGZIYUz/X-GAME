package com.example.backend.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * redisson配置
 */
@Configuration
public class RedissionConfig {

    @Bean
    public RedissonClient redissonClient() {
        // 配置
        Config config = new Config();
        String redisAddress = "redis://127.0.0.1:6379";
        config.useSingleServer().setAddress(redisAddress).setDatabase(3);
        // 创建实例
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }
}
