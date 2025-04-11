package com.example.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.backend.model.domain.CommentRecord;
import com.example.backend.model.vo.CommentVO;
import com.example.backend.service.CommentRecordService;
import com.example.backend.mapper.CommentRecordMapper;
import com.example.backend.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author szs
 * @description 针对表【comment_record(社区评论)】的数据库操作Service实现
 * @createDate 2024-11-05 09:33:47
 */
@Service
public class CommentRecordServiceImpl extends ServiceImpl<CommentRecordMapper, CommentRecord>
        implements CommentRecordService {

    @Resource
    private UserService userService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<CommentVO> getByType(Integer type) {
        QueryWrapper<CommentRecord> queryWrapper = new QueryWrapper();
        queryWrapper.eq("type", type);
        List<CommentRecord> records = this.list(queryWrapper);
        List<CommentVO> comments = new ArrayList<>();
        for (CommentRecord oneRecord : records) {
            CommentVO commentVO = new CommentVO();
            String owner = oneRecord.getOwner();
            Long id = userService.getIdByName(owner);
            String comment = oneRecord.getComment();
            commentVO.setUserId(id);
            commentVO.setUserName(owner);
            commentVO.setContent(comment);
            comments.add(commentVO);
        }
        return comments;
    }
}




