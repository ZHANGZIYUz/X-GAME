package com.example.backend.mapper;

import com.example.backend.model.domain.ChatRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author szs
* @description 针对表【chat_record(聊天记录)】的数据库操作Mapper
* @createDate 2024-11-04 17:37:33
* @Entity generator.domain.ChatRecord
*/
@Mapper
public interface ChatRecordMapper extends BaseMapper<ChatRecord> {

}




