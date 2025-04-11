package com.example.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.backend.common.ErrorCode;
import com.example.backend.exception.MyException;
import com.example.backend.mapper.UserMapper;
import com.example.backend.model.domain.User;
import com.example.backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.example.backend.contant.UserContant.ADMIN_ROLE;
import static com.example.backend.contant.UserContant.USER_LOGIN_STATE;

/**
 * @author szs
 * @description 针对表【user(用户)】的数据库操作Service实现
 * @createDate 2024-09-26 19:09:32
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public long userRegister(User user) {
        // 1.校验
        if (StringUtils.isAnyBlank(user.getUserAccount(), user.getUserPassword())) {
            throw new MyException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (user.getUserAccount().length() < 4) {
            throw new MyException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        if (user.getUserPassword().length() < 4) {
            throw new MyException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", user.getUserAccount());
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new MyException(ErrorCode.PARAMS_ERROR, "用户账号重复");
        }
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUsername());
        count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new MyException(ErrorCode.PARAMS_ERROR, "用户名重复");
        }
        // 2.加密
        String encryptPassword = DigestUtils.md5DigestAsHex(user.getUserPassword().getBytes());
        // 3.插入数据
        user.setUserPassword(encryptPassword);
        String tags = user.getTags();
        Integer gender = user.getGender();
        String result;
        if (gender == 0) {
            result = "[\"女\",\"" + tags.replaceAll(",", "\",\"") + "\"]";
        } else {
            result = "[\"男\",\"" + tags.replaceAll(",", "\",\"") + "\"]";
        }
        user.setTags(result);
        int insert = userMapper.insert(user);
        if (insert <= 0) {
            throw new MyException(ErrorCode.PARAMS_ERROR);
        }
        return user.getId();
    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }
        String encryptPassword = DigestUtils.md5DigestAsHex(userPassword.getBytes());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            log.info("user login failed, userAccount cannot match userPassword!");
            throw new MyException(ErrorCode.LOGININ_ERROR);
        }
        User safetyUser = new User();
        safetyUser.setId(user.getId());
        safetyUser.setUsername(user.getUsername());
        safetyUser.setUserAccount(user.getUserAccount());
        safetyUser.setAvatarUrl(user.getAvatarUrl());
        safetyUser.setGender(user.getGender());
        safetyUser.setUserStatus(user.getUserStatus());
        safetyUser.setCreateTime(user.getCreateTime());
        safetyUser.setUserRole(user.getUserRole());
        //记录用户登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, safetyUser);
        return safetyUser;
    }

    @Override
    public int userLogout(HttpServletRequest request) {
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;
    }

    @Override
    public List<User> searchUserByTags(List<String> tagNameList) {
        if (CollectionUtils.isEmpty(tagNameList)) {
            throw new MyException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        for (String tagName : tagNameList) {
            QueryWrapper<User> like = queryWrapper.like("tags", tagName);
        }
        List<User> userList = userMapper.selectList(queryWrapper);
        return userList;
    }

    @Override
    public int updateUser(User user, User loginUser) {
        long userId = user.getId();
        if (userId <= 0) {
            throw new MyException(ErrorCode.PARAMS_ERROR);
        }
        // 如果是管理员，或者用户本人可以修改
        if (user.getId() == loginUser.getId() || isAdmin(loginUser)) {
            return userMapper.updateById(user);
        }
        throw new MyException(ErrorCode.NO_AUTH);
    }

    @Override
    public User getLoginUser(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        if (userObj == null) {
            throw new MyException(ErrorCode.NO_AUTH);
        }
        return (User) userObj;
    }

    @Override
    public Boolean isAdmin(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        if (user.getUserRole() != ADMIN_ROLE || user == null) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean isAdmin(User loginUser) {
        return loginUser != null && loginUser.getUserRole() == ADMIN_ROLE;
    }

    @Override
    public Long getIdByName(String userName) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", userName);
        User user = this.getOne(queryWrapper);
        return user.getId();
    }

    @Override
    public boolean deleteUser(Long deleteId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", deleteId);
        int delete = userMapper.delete(queryWrapper);
        if (delete > 0) {
            return true;
        } else {
            return false;
        }
    }
}




