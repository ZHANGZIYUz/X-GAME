package com.example.backend.common;

import lombok.Data;

/**
 * 通用分页请求参数
 */
@Data
public class PageRequest {

    /**
     * 页面大小
     */
    protected int pageSize = 10;

    /**
     * 当前页数
     */
    protected int pageNum = 1;
}
