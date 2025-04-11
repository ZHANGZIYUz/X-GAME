package com.example.backend.service;

import com.example.backend.model.domain.Team;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.backend.model.domain.User;
import com.example.backend.model.domain.UserTeam;
import com.example.backend.model.dto.TeamQuery;
import com.example.backend.model.request.TeamJoinRequest;
import com.example.backend.model.request.TeamQuitRequest;
import com.example.backend.model.request.TeamUpdateRequest;
import com.example.backend.model.vo.TeamUserVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author szs
* @description 针对表【team(队伍)】的数据库操作Service
* @createDate 2024-10-20 13:39:35
*/
public interface TeamService extends IService<Team> {

    /**
     * 创建队伍
     * @param team
     * @return
     */
    long addTeam(Team team, User loginUser);

    /**
     * 搜索队伍
     * @param teamQuery
     * @return
     */
    List<TeamUserVO> listTeams(TeamQuery teamQuery, boolean isAdmin);

    /**
     * 更新队伍信息
     * @param teamUpdateRequest
     * @param loginUser
     * @return
     */
    boolean updateTeam(TeamUpdateRequest teamUpdateRequest, User loginUser);

    /**
     * 加入队伍
     * @param teamJoinRequest
     * @param loginUser
     * @return
     */
    boolean joinTeam(TeamJoinRequest teamJoinRequest, User loginUser);

    /**
     * 退出队伍
     * @param teamQuitRequest
     * @param loginUser
     * @return
     */
    boolean quitTeam(TeamQuitRequest teamQuitRequest, User loginUser);

    /**
     * 解散队伍
     * @param id
     * @param loginUser
     * @return
     */
    boolean deleteTeam(long id, User loginUser);

    /**
     * 查找自己加入的队伍
     * @param loginUser
     * @return
     */
    List<Team> listMyJoinTeams(User loginUser);

    /**
     * 查找自己创建的队伍
     * @param loginUser
     * @return
     */
    List<Team> listMyCreateTeams(User loginUser);

    /**
     * 根据队伍寻找队员
     * @param id
     * @return
     */
    List<User> searchTeamMembers(long id);
}
