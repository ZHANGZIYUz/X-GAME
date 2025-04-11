package com.example.backend.model.vo;

import lombok.Data;

/**
 * 评论封装类
 */

@Data
public class CommentVO {

    private Long userId;

    private String userName;

    private String content;
}
