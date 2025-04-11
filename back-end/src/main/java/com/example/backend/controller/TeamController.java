package com.example.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.backend.common.DeleteRequest;
import com.example.backend.common.ErrorCode;
import com.example.backend.common.Result;
import com.example.backend.common.ResultUtils;
import com.example.backend.exception.MyException;
import com.example.backend.model.domain.Team;
import com.example.backend.model.domain.User;
import com.example.backend.model.dto.TeamQuery;
import com.example.backend.model.request.TeamAddRequest;
import com.example.backend.model.request.TeamJoinRequest;
import com.example.backend.model.request.TeamQuitRequest;
import com.example.backend.model.request.TeamUpdateRequest;
import com.example.backend.model.vo.TeamUserVO;
import com.example.backend.service.TeamService;
import com.example.backend.service.UserService;
import com.example.backend.service.UserTeamService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 组队接口
 */
@RestController
@RequestMapping("/team")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
//@CrossOrigin(origins = "http://120.27.243.219", allowCredentials = "true")
@Slf4j
public class TeamController {

    @Resource
    private UserService userService;

    @Resource
    private TeamService teamService;

    @Resource
    private UserTeamService userTeamService;

    @PostMapping("/add")
    public Result<Long> addTeam(@RequestBody TeamAddRequest teamAddRequest, HttpServletRequest request) {
        if (teamAddRequest == null) {
            throw new MyException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        Team team = new Team();
        try {
            BeanUtils.copyProperties(team, teamAddRequest);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        long teamId = teamService.addTeam(team, loginUser);
        return ResultUtils.sucess(teamId);
    }

    @DeleteMapping("/delete")
    public Result<Boolean> deleteTeam(@RequestParam long id) {
        if (id <= 0) {
            throw new MyException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = teamService.removeById(id);
        if (!result) {
            throw new MyException(ErrorCode.SYSTEM_ERROR, "删除失败");
        }
        return ResultUtils.sucess(true);
    }

    @PostMapping("/update")
    public Result<Boolean> updateTeam(@RequestBody TeamUpdateRequest teamUpdateRequest, HttpServletRequest request) {
        if (teamUpdateRequest == null) {
            throw new MyException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        boolean result = teamService.updateTeam(teamUpdateRequest, loginUser);
        if (!result) {
            throw new MyException(ErrorCode.SYSTEM_ERROR, "更新失败");
        }
        return ResultUtils.sucess(true);
    }

    @GetMapping("/get")
    public Result<Team> getTeamById(long id) {
        if (id <= 0) {
            throw new MyException(ErrorCode.PARAMS_ERROR);
        }
        Team team = teamService.getById(id);
        if (team == null) {
            throw new MyException(ErrorCode.NULL_ERROR);
        }
        return ResultUtils.sucess(team);
    }

    @GetMapping("/list")
    public Result<List<TeamUserVO>> listTeams(TeamQuery teamQuery, HttpServletRequest request) {
        if (teamQuery == null) {
            throw new MyException(ErrorCode.PARAMS_ERROR);
        }
        Boolean isAdmin = userService.isAdmin(request);
        List<TeamUserVO> teamList = teamService.listTeams(teamQuery, isAdmin);
        return ResultUtils.sucess(teamList);
    }

    @GetMapping("/list/page")
    public Result<Page<Team>> listTeamsPage(TeamQuery teamQuery) {
        if (teamQuery == null) {
            throw new MyException(ErrorCode.PARAMS_ERROR);
        }
        Team team = new Team();
        try {
            BeanUtils.copyProperties(team, teamQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Page page = new Page(teamQuery.getPageNum(), teamQuery.getPageSize());
        QueryWrapper<Team> queryWrapper = new QueryWrapper<>(team);
        Page<Team> teamPage = teamService.page(page, queryWrapper);
        return ResultUtils.sucess(teamPage);
    }

    @PostMapping("/join")
    public Result<Boolean> joinTeam(@RequestBody TeamJoinRequest teamJoinRequest, HttpServletRequest request) {
        if (teamJoinRequest == null) {
            throw new MyException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        boolean result = teamService.joinTeam(teamJoinRequest, loginUser);
        return ResultUtils.sucess(result);
    }

    @PostMapping("/quit")
    public Result<Boolean> quitTeam(@RequestBody TeamQuitRequest teamQuitRequest, HttpServletRequest request) {
        if (teamQuitRequest == null) {
            throw new MyException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        boolean result = teamService.quitTeam(teamQuitRequest, loginUser);
        return ResultUtils.sucess(result);
    }

    @PostMapping("/delete")
    public Result<Boolean> deleteTeam(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new MyException(ErrorCode.PARAMS_ERROR);
        }
        long id = deleteRequest.getId();
        User loginUser = userService.getLoginUser(request);
        boolean result = teamService.deleteTeam(id, loginUser);
        if (!result) {
            throw new MyException(ErrorCode.SYSTEM_ERROR, "删除失败");
        }
        return ResultUtils.sucess(true);
    }

    @GetMapping("/list/my/join")
    public Result<List<Team>> listMyJoinTeams(HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        List<Team> teamList = teamService.listMyJoinTeams(loginUser);
        return ResultUtils.sucess(teamList);
    }

    @GetMapping("/list/my/create")
    public Result<List<Team>> listMyCreateTeams(HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        List<Team> teamList = teamService.listMyCreateTeams(loginUser);
        return ResultUtils.sucess(teamList);
    }

    @PostMapping("/search/member")
    public Result<List<User>> searchTeamMembers(Long teamId) {
        List<User> userList = teamService.searchTeamMembers(teamId);
        return ResultUtils.sucess(userList);
    }
}
