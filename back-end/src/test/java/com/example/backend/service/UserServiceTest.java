package com.example.backend.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.example.backend.model.domain.Game;
import com.example.backend.model.domain.User;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Resource
    private UserService userService;

    @Resource
    private GameService gameService;

    @Test
    void testAddUser() {
        User user = new User();
        user.setUsername("test1");
        user.setUserAccount("123");
        user.setAvatarUrl("https://th.bing.com/th/id/OIP.wpCgjzigc8llfVXalEJDYAHaEK?rs=1&pid=ImgDetMain");
        user.setGender(0);
        user.setUserPassword("766230");
        boolean result = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(result);
    }

    @Test
    void setGameServiceRegister() {
        for (int i = 0; i < 10; i++) {
            Game testGame = new Game();
            testGame.setGameName("test2");
            testGame.setAvatarUrl("https://th.bing.com/th/id/OIP.wpCgjzigc8llfVXalEJDYAHaEK?rs=1&pid=ImgDetMain");
            testGame.setType(1);
            gameService.save(testGame);
        }
    }

    @Test
    public void testSearchUserByTags() {
        List<String> tagNameList = Arrays.asList("java", "python");
        List<User> list = userService.searchUserByTags(tagNameList);
        Assert.assertNotNull(list);
    }


}