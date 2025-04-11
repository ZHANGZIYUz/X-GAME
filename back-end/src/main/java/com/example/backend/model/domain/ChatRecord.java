package com.example.backend.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 聊天记录
 * @TableName chat_record
 */
@TableName(value ="chat_record")
@Data
public class ChatRecord implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 聊天记录
     */
    private String record;

    /**
     * 发送用户
     */
    private String sendUser;

    /**
     * 接收用户
     */
    private String receiveUser;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}