package com.example.backend.service;

import com.example.backend.model.domain.User;
import com.example.backend.model.domain.UserFriendRelation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author szs
* @description 针对表【user_friend_relation(用户好友列表)】的数据库操作Service
* @createDate 2024-10-23 09:32:44
*/
public interface UserFriendRelationService extends IService<UserFriendRelation> {

    /**
     * 好友添加功能
     * @param friendUserId
     * @param loginUser
     */
    boolean addFriends(long friendUserId, User loginUser);

    /**
     * 好友查询功能
     * @param loginUser
     * @return
     */
    List<User> searchFriends(User loginUser);

    /**
     * 删除好友
     * @param friendUserId
     * @param loginUser
     * @return
     */
    boolean deleteFriends(Long friendUserId, User loginUser);

}
