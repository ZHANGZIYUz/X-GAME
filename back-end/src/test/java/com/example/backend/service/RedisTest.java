package com.example.backend.service;

import com.example.backend.model.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;

@SpringBootTest
public class RedisTest {

    @Resource
    private RedisTemplate redisTemplate;

    @Test
    void test() {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("test1","string1");
        valueOperations.set("test2","string2");
        valueOperations.set("test3","string3");
        User user = new User();
        user.setId(0L);
        user.setUsername("zzy");
        valueOperations.set("user",user);
        Object test1 = valueOperations.get("test1");
        System.out.println(test1);
        Object user1 = valueOperations.get("user");
        System.out.println(user1);
    }
}
