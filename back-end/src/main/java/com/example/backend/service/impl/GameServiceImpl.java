package com.example.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.backend.common.ErrorCode;
import com.example.backend.exception.MyException;
import com.example.backend.model.domain.Game;
import com.example.backend.service.GameService;
import com.example.backend.mapper.GameMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author szs
 * @description 针对表【game(游戏)】的数据库操作Service实现
 * @createDate 2024-10-19 09:31:07
 */
@Service
public class GameServiceImpl extends ServiceImpl<GameMapper, Game>
        implements GameService {

    @Resource
    private GameMapper gameMapper;

    @Override
    public int updateGame(Integer gameId, Integer score) {
        QueryWrapper<Game> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", gameId);
        Game finalGame = gameMapper.selectOne(queryWrapper);
        if (finalGame == null) {
            throw new MyException(ErrorCode.DATABASE_ERROR);
        }
        Double averageScore = finalGame.getAverageScore();
        Integer number = finalGame.getNumber();
        averageScore = (averageScore * number + score) / (number + 1);
        number++;
        Game updateGame = new Game();
        updateGame.setId((long) gameId);
        updateGame.setGameName(finalGame.getGameName());
        updateGame.setAvatarUrl(finalGame.getAvatarUrl());
        updateGame.setNumber(number);
        updateGame.setAverageScore(averageScore);
        return gameMapper.updateById(updateGame);
    }
}




