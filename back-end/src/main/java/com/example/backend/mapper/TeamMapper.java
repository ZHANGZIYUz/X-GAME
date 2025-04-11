package com.example.backend.mapper;

import com.example.backend.model.domain.Team;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author szs
* @description 针对表【team(队伍)】的数据库操作Mapper
* @createDate 2024-10-22 12:56:03
* @Entity generator.domain.Team
*/
@Mapper
public interface TeamMapper extends BaseMapper<Team> {

}




