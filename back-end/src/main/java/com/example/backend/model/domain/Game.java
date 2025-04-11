package com.example.backend.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 游戏
 */
@TableName(value ="game")
@Data
public class Game implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 游戏名称
     */
    private String gameName;

    /**
     * 游戏头像
     */
    private String avatarUrl;

    /**
     * 打分人数
     */
    private Integer number;

    /**
     * 游戏平均分
     */
    private Double averageScore;

    /**
     * 游戏类型
     * 大型多人在线角色扮演游戏 (MMORPG)', '多人在线战术竞技游戏 (MOBA)',
     *   '第一人称射击游戏 (FPS)', '生存游戏 (Survival Games)', '沙盒游戏 (Sandbox Games)',
     *   '战略游戏 (Strategy Games)', '竞速游戏 (Racing Games)', '卡牌游戏 (Card Games)',
     *   '音乐节奏游戏 (Rhythm Games)', '体育游戏 (Sports Games)', '动作角色扮演游戏 (Action RPG)',
     *   '动作冒险游戏 (Action-Adventure)', '格斗游戏 (fighting Games)
     */
    private Integer type;

    /**
     * 游戏简介
     */
    private String profile;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}