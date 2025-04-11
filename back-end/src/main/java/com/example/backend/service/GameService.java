package com.example.backend.service;

import com.example.backend.model.domain.Game;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author szs
 * @description 针对表【game(游戏)】的数据库操作Service
 * @createDate 2024-10-19 09:31:07
 */
public interface GameService extends IService<Game> {

    /**
     * 根据id修改游戏的信息
     *
     * @param gameId
     */
    int updateGame(Integer gameId, Integer score);
}
