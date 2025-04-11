package com.example.backend.service;

import com.example.backend.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author szs
 * @description 针对表【user(用户)】的数据库操作Service
 * @createDate 2024-09-26 19:09:32
 */
public interface UserService extends IService<User> {
    /**
     * 用户注册
     * @param user
     * @return
     */
    long userRegister(User user);

    /**
     * 用户登录
     *
     * @param userAccount
     * @param userPassword
     * @param request
     * @return 用户信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    int userLogout(HttpServletRequest request);

    /**
     * 根据标签查询用户
     *
     * @param tagNameList
     * @return
     */
    List<User> searchUserByTags(List<String> tagNameList);

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    int updateUser(User user, User loginUser);

    /**
     * 获取当前用户的信息
     *
     * @return
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 是否为管理员
     *
     * @param request
     * @return
     */
    public Boolean isAdmin(HttpServletRequest request);

    /**
     * 是否为管理员
     *
     * @param loginUser
     * @return
     */
    public Boolean isAdmin(User loginUser);

    /**
     * 根据用户名称获取id
     * @param userName
     * @return
     */
    public Long getIdByName(String userName);

    /**
     * 删除用户信息
     * @param deleteId
     * @return
     */
    boolean deleteUser(Long deleteId);
}
