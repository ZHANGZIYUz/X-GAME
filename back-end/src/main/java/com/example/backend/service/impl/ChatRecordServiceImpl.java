package com.example.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.backend.model.domain.ChatRecord;
import com.example.backend.service.ChatRecordService;
import com.example.backend.mapper.ChatRecordMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author szs
 * @description 针对表【chat_record(聊天记录)】的数据库操作Service实现
 * @createDate 2024-11-04 17:37:33
 */
@Service
public class ChatRecordServiceImpl extends ServiceImpl<ChatRecordMapper, ChatRecord>
        implements ChatRecordService {

    @Override
    public List<String> getByName(String currentUser, String receiveUser) {
        QueryWrapper<ChatRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .and(wrapper ->
                        wrapper.eq("sendUser", currentUser).eq("receiveUser", receiveUser)
                                .or()
                                .eq("sendUser", receiveUser).eq("receiveUser", currentUser)
                )
                .orderByAsc("id");
        List<ChatRecord> records = this.list(queryWrapper);// 根据主键升序排列
        List<String> chatRecord = new ArrayList<>();
        for (ChatRecord oneRecord : records) {
            chatRecord.add(oneRecord.getSendUser() + ": " + oneRecord.getRecord());
        }
        return chatRecord;
    }
}




