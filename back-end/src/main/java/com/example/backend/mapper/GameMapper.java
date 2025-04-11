package com.example.backend.mapper;

import com.example.backend.model.domain.Game;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author szs
* @description 针对表【game(游戏)】的数据库操作Mapper
* @createDate 2024-10-20 19:10:18
* @Entity generator.domain.Game
*/
@Mapper
public interface GameMapper extends BaseMapper<Game> {

}




