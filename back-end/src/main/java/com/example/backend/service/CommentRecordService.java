package com.example.backend.service;

import com.example.backend.model.domain.CommentRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.backend.model.vo.CommentVO;

import java.util.List;

/**
* @author szs
* @description 针对表【comment_record(社区评论)】的数据库操作Service
* @createDate 2024-11-05 09:33:47
*/
public interface CommentRecordService extends IService<CommentRecord> {

    /**
     * 根据分区获取评论
     * @param type
     * @return
     */
    List<CommentVO> getByType(Integer type);
}
