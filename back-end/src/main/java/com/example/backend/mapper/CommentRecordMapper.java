package com.example.backend.mapper;

import com.example.backend.model.domain.CommentRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author szs
* @description 针对表【comment_record(社区评论)】的数据库操作Mapper
* @createDate 2024-11-05 09:33:47
* @Entity generator.domain.CommentRecord
*/
@Mapper
public interface CommentRecordMapper extends BaseMapper<CommentRecord> {

}




