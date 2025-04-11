package com.example.backend.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.backend.common.ResultUtils;
import com.example.backend.mapper.UserMapper;
import com.example.backend.model.domain.User;
import com.example.backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 缓存预热
 */
@Component
@Slf4j
public class PreCacheJob {

//    @Resource
//    private UserService userService;
//
//    @Resource
//    private RedisTemplate<String, Object> redisTemplate;
//
//    @Resource
//    private RedissonClient redissonClient;
//
//    // 重点用户，预热用户id
//    private List<Long> mainUserList = Arrays.asList(1L);
//
//    // 每天执行,预热推荐用户,cron表达式：秒，分，时，日，月，年
//    @Scheduled(cron = "0 59 23 * * *")
//    public void doCahceRecommendUser() {
//        RLock lock = redissonClient.getLock("zzy:preCache:lock");
//        try {
//            if (lock.tryLock(0, -1, TimeUnit.MILLISECONDS)) {
//                // 重点用户
//                for (Long userId : mainUserList) {
//                    String redisKey = String.format("zzy:user:recommend:%S", userId);
//                    ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
//                    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//                    Page<User> userPage = userService.page(new Page<>(1, 10), queryWrapper);
//                    try {
//                        valueOperations.set(redisKey, userPage, 30000, TimeUnit.MILLISECONDS);
//                    } catch (Exception e) {
//                        log.error("redis set key error", e);
//                    }
//                }
//            }
//        } catch (InterruptedException e) {
//            log.error("doCacheError error" + e);
//        } finally {
//            // 只能释放自己的锁
//            if (lock.isHeldByCurrentThread()) {
//                lock.unlock();
//            }
//        }
//    }
}
