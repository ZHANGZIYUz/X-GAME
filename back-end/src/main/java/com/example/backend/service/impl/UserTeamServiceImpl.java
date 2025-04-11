package com.example.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.backend.model.domain.UserTeam;
import com.example.backend.service.UserTeamService;
import com.example.backend.mapper.UserTeamMapper;
import org.springframework.stereotype.Service;

/**
* @author szs
* @description 针对表【user_team(用户队伍关系)】的数据库操作Service实现
* @createDate 2024-10-20 13:41:13
*/
@Service
public class UserTeamServiceImpl extends ServiceImpl<UserTeamMapper, UserTeam>
    implements UserTeamService{

}




