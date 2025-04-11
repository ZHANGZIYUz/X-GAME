package com.example.backend.service;

import com.example.backend.mapper.UserMapper;
import com.example.backend.model.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class InsertUsersTest {

    @Resource
    private UserMapper userMapper;

//    @Test
//    public void doInsertUsers() {
//        final int INSERT_NUM = 100;
//        for (int i = 0; i < INSERT_NUM; i++) {
//            User user = new User();
//            user.setUsername("测试用户");
//            user.setUserAccount("fake");
//            user.setAvatarUrl("https://images.unsplash.com/photo-1503023345310-bd7c1de61c7d?w=500");
//            user.setGender(0);
//            user.setUserPassword("123456789");
//            user.setPhone("123");
//            user.setEmail("123");
//            user.setUserStatus(0);
//            user.setIsDelete(0);
//            user.setUserRole(0);
//            user.setPlanetCode("1111");
//            user.setTags("[]");
//            userMapper.insert(user);
//        }
//    }
}
