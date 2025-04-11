package com.example.backend.model.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户好友列表
 * @TableName user_friend_relation
 */
@TableName(value ="user_friend_relation")
@Data
public class UserFriendRelation implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 主用户id
     */
    private Long mainUserId;

    /**
     * 主用户好友id
     */
    private Long friendUserId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}