package com.example.backend.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 社区评论
 * @TableName comment_record
 */
@TableName(value ="comment_record")
@Data
public class CommentRecord implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 评论
     */
    private String comment;

    /**
     * 游戏类型
     */
    private Integer type;

    /**
     * 评论发起人
     */
    private String owner;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}