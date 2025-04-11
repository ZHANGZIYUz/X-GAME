package com.example.backend.service;

import org.junit.jupiter.api.Test;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class redissonTest {
    @Resource

    private RedissonClient redissonClient;

    @Test
    void test(){
        RList<Object> rlist = redissonClient.getList("rlist");
        rlist.add("zzy");
        Object o = rlist.get(0);
        System.out.println("rlist: "+o);
    }
}
