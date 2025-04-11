package com.example.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.backend.common.ErrorCode;
import com.example.backend.exception.MyException;
import com.example.backend.mapper.UserMapper;
import com.example.backend.model.domain.User;
import com.example.backend.model.domain.UserFriendRelation;
import com.example.backend.service.UserFriendRelationService;
import com.example.backend.mapper.UserFriendRelationMapper;
import com.example.backend.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author szs
 * @description 针对表【user_friend_relation(用户好友列表)】的数据库操作Service实现
 * @createDate 2024-10-23 09:32:44
 */
@Service
public class  UserFriendRelationServiceImpl extends ServiceImpl<UserFriendRelationMapper, UserFriendRelation>
        implements UserFriendRelationService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserFriendRelationMapper userFriendRelationMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addFriends(long friendUserId, User loginUser) {
        long userId = loginUser.getId();
        if (userId == friendUserId) {
            throw new MyException(ErrorCode.PARAMS_ERROR, "添加好友失败,不能加自己好友");
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("mainUserId", userId);
        queryWrapper.eq("friendUserId", friendUserId);
        long count = this.count(queryWrapper);
        if (count > 0) {
            throw new MyException(ErrorCode.PARAMS_ERROR, "添加好友失败,已经添加过该好友");
        }
        UserFriendRelation relation = new UserFriendRelation();
        relation.setMainUserId(userId);
        relation.setFriendUserId(friendUserId);
        relation.setCreateTime(new Date());
        boolean save = this.save(relation);
        if (!save) {
            throw new MyException(ErrorCode.PARAMS_ERROR, "插入用户好友关系失败");
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<User> searchFriends(User loginUser) {
        List<User> friendList = new ArrayList<>();
        long userId = loginUser.getId();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("mainUserId", userId);
        List<UserFriendRelation> relationshipList = this.list(queryWrapper);
        for (UserFriendRelation relation : relationshipList) {
            long friendUserId = relation.getFriendUserId();
            queryWrapper = new QueryWrapper();
            queryWrapper.eq("id", friendUserId);
            User friendsUser = userMapper.selectOne(queryWrapper);
            friendList.add(friendsUser);
        }
        queryWrapper = new QueryWrapper();
        queryWrapper.eq("friendUserId",userId);
        relationshipList = this.list(queryWrapper);
        for (UserFriendRelation relation : relationshipList) {
            long mainUserId = relation.getMainUserId();
            queryWrapper = new QueryWrapper();
            queryWrapper.eq("id", mainUserId);
            User friendsUser = userMapper.selectOne(queryWrapper);
            friendList.add(friendsUser);
        }
        if (friendList == null || friendList.size() < 1) {
            throw new MyException(ErrorCode.PARAMS_ERROR, "该用户暂无好友");
        }
        return friendList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteFriends(Long friendUserId, User loginUser) {
        long userId = loginUser.getId();
        QueryWrapper<UserFriendRelation> queryWrapper = new QueryWrapper();
        queryWrapper.and(wrapper ->
                wrapper.eq("mainUserId", userId).eq("friendUserId", friendUserId)
                        .or()
                        .eq("mainUserId", friendUserId).eq("friendUserId", userId)
        );
        long count = this.count(queryWrapper);
        if (count < 1) {
            throw new MyException(ErrorCode.PARAMS_ERROR, "删除好友失败,好友关系不存在");
        }
        UserFriendRelation relation = userFriendRelationMapper.selectOne(queryWrapper);
        Long id = relation.getId();
        queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", id);
        int delete = userFriendRelationMapper.delete(queryWrapper);
        if (delete < 1) {
            throw new MyException(ErrorCode.PARAMS_ERROR, "删除好友失败");
        }
        return true;
    }
}




