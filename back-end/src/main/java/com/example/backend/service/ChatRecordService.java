package com.example.backend.service;

import com.example.backend.model.domain.ChatRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author szs
* @description 针对表【chat_record(聊天记录)】的数据库操作Service
* @createDate 2024-11-04 17:37:33
*/
public interface ChatRecordService extends IService<ChatRecord> {

    /**
     * 通过姓名查找相对应的聊天记录
     * @param currentUser
     * @return
     */
    List<String> getByName(String currentUser, String receiveUser);
}
