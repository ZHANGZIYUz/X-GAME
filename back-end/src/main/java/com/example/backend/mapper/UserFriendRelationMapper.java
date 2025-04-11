package com.example.backend.mapper;

import com.example.backend.model.domain.UserFriendRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author szs
* @description 针对表【user_friend_relation(用户好友列表)】的数据库操作Mapper
* @createDate 2024-10-23 09:32:44
* @Entity generator.domain.UserFriendRelation
*/
@Mapper
public interface UserFriendRelationMapper extends BaseMapper<UserFriendRelation> {

}




