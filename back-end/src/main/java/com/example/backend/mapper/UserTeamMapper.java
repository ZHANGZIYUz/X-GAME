package com.example.backend.mapper;

import com.example.backend.model.domain.UserTeam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author szs
* @description 针对表【user_team(用户队伍关系)】的数据库操作Mapper
* @createDate 2024-10-20 19:10:13
* @Entity generator.domain.UserTeam
*/
@Mapper
public interface UserTeamMapper extends BaseMapper<UserTeam> {

}




