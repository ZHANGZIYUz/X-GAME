package com.example.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.backend.common.ErrorCode;
import com.example.backend.common.Result;
import com.example.backend.common.ResultUtils;
import com.example.backend.exception.MyException;
import com.example.backend.model.domain.CommentRecord;
import com.example.backend.model.domain.Game;
import com.example.backend.model.domain.User;
import com.example.backend.model.request.UserLoginRequest;
import com.example.backend.model.request.UserRegisterRequest;
import com.example.backend.model.vo.CommentVO;
import com.example.backend.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static com.example.backend.contant.UserContant.USER_LOGIN_STATE;

/**
 * 用户接口
 */
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
//@CrossOrigin(origins = "http://120.27.243.219", allowCredentials = "true")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private GameService gameService;

    @Resource
    private UserFriendRelationService userFriendRelationService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private ChatRecordService chatRecordService;

    @Resource
    private CommentRecordService commentRecordService;

    @PostMapping("/register")
    public Result<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new MyException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        User user = new User();
        try {
            BeanUtils.copyProperties(user, userRegisterRequest);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        user.setId(null);
        long id = userService.userRegister(user);
        if (id > 0) {
            return new Result<>(0, id, "注册成功");
        } else {
            return new Result<>(1, id, "注册失败");
        }
    }

    @PostMapping("/login")
    public Result<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            return null;
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }
        User user = userService.userLogin(userAccount, userPassword, request);
        return new Result<>(0, user, "登录成功");
    }

    @PostMapping("/logout")
    public Result<Integer> userLogout(HttpServletRequest request) {
        if (request == null) {
            return ResultUtils.fail(ErrorCode.PARAMS_ERROR);
        }
        int result = userService.userLogout(request);
        return new Result<>(0, result, "注销成功");
    }

    @GetMapping("/current")
    public Result<User> getCurrentUser(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null) {
            return ResultUtils.fail(ErrorCode.NO_LOGIN);
        }
        Long id = currentUser.getId();
        User user = userService.getById(id);
        return new Result<>(0, user, "成功获取当前用户");
    }

    @GetMapping("/search")
    public Result<List<User>> searchUsers(String username, HttpServletRequest request) {
        if (!userService.isAdmin(request)) {
            return null;
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like("username", username);
        }
        List<User> list = userService.list(queryWrapper);
        return new Result<>(0, list, "成功获取用户");
    }

    @GetMapping("/searchOne")
    public Result<User> searchOneUserById(@RequestParam Long userOneId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        User oneUser = userService.getById(userOneId);
        if (oneUser == null) {
            throw new MyException(ErrorCode.PARAMS_ERROR, "获取该用户失败");
        }
        return new Result<>(0, oneUser, "成功获取该用户");
    }

    @PostMapping("/delete")
    public Result<Boolean> deleteUser(@RequestBody long id, HttpServletRequest request) {
        if (!userService.isAdmin(request)) {
            return new Result<>(1, false, "删除用户失败");
        }
        if (id <= 0) {
            return new Result<>(1, false, "删除用户失败");
        }
        boolean b = userService.removeById(id);
        return new Result<>(0, b, "成功删除用户");
    }

    @GetMapping("/search/tags")
    public Result<List<User>> searchUserByTags(@RequestParam(required = false) List<String> tagNameList) {
        if (tagNameList == null) {
            throw new MyException(ErrorCode.PARAMS_ERROR);
        }
        List<User> userList = userService.searchUserByTags(tagNameList);
        if (userList.isEmpty()) {
            return ResultUtils.fail(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.sucess(userList);
    }

//    @GetMapping("/recommend")
//    public Result<Page<User>> recommendUsers(Long pageSize, Long pageNum, HttpServletRequest request) {
//        User loginUser = userService.getLoginUser(request);
//        String redisKey = String.format("zzy:user:recommend:%S", loginUser.getId());
//        // 查询缓存里面有没有用户信息。
//        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
//        Page<User> userPage = (Page<User>) valueOperations.get(redisKey);
//        if (userPage != null) {
//            return ResultUtils.sucess(userPage);
//        }
//        // 无缓存，查数据库,写缓存
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        userPage = userService.page(new Page<>(pageNum, pageSize), queryWrapper);
//        return ResultUtils.sucess(userPage);
//    }

    @PostMapping("/update")
    public Result<Integer> udpateUser(@RequestBody User user, HttpServletRequest request) {
        if (user == null) {
            throw new MyException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        int result = userService.updateUser(user, loginUser);
        return ResultUtils.sucess(result);
    }

    @GetMapping("/search/games")
    public Result<List<Game>> searchGames(@RequestParam int gameType) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("type", gameType);
        List<Game> gameList = gameService.list(queryWrapper);
        if (gameList.isEmpty()) {
            return ResultUtils.fail(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.sucess(gameList);
    }

    @PostMapping("/game/update")
    public Result<Integer> updatedGame(@RequestParam Integer gameId, Integer score) {
        if (gameId == null) {
            throw new MyException(ErrorCode.PARAMS_ERROR);
        }
        int result = gameService.updateGame(gameId, score);
        if (result > 0) {
            return ResultUtils.sucess(result);
        }
        throw new MyException(ErrorCode.DATABASE_ERROR);
    }

    @PostMapping("/friend/add")
    public Result<Boolean> addFriends(@RequestParam Long friendUserId, HttpServletRequest request) {
        if (friendUserId == null) {
            throw new MyException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        boolean result = userFriendRelationService.addFriends(friendUserId, loginUser);
        return ResultUtils.sucess(result);
    }

    @PostMapping("/friend/search")
    public Result<List<User>> searchFriends(HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        List<User> friendsList = userFriendRelationService.searchFriends(loginUser);
        return ResultUtils.sucess(friendsList);
    }

    @DeleteMapping("/friend/delete")
    public Result<Boolean> deleteFriends(@RequestParam Long friendUserId, HttpServletRequest request) {
        if (friendUserId == null) {
            throw new MyException(ErrorCode.PARAMS_ERROR, "好友id为空");
        }
        User loginUser = userService.getLoginUser(request);
        boolean result = userFriendRelationService.deleteFriends(friendUserId, loginUser);
        return ResultUtils.sucess(result);
    }

    @GetMapping("/chat/record")
    public Result<List<String>> getChatRecord(String currentUser, String receiveUser) {
        List<String> records = chatRecordService.getByName(currentUser, receiveUser);
        return ResultUtils.sucess(records);
    }

    @GetMapping("/search/comment")
    public Result<List<CommentVO>> getCommentRecord(Integer type) {
        List<CommentVO> comment = commentRecordService.getByType(type);
        return ResultUtils.sucess(comment);
    }

    @GetMapping("/add/comment")
    public Result<Boolean> addComment(String content, String type, HttpServletRequest request) {
        CommentRecord commentRecord = new CommentRecord();
        User loginUser = userService.getLoginUser(request);
        String username = loginUser.getUsername();
        commentRecord.setOwner(username);
        commentRecord.setComment(content);
        int gameType = Integer.parseInt(type);
        commentRecord.setType(gameType);
        boolean save = commentRecordService.save(commentRecord);
        if (!save) {
            throw new MyException(ErrorCode.PARAMS_ERROR, "评论失败");
        }
        return ResultUtils.sucess(true);
    }

    @GetMapping("/deleteAccount")
    public Result<Boolean> deleteAccount(HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        Long deleteId = loginUser.getId();
        boolean result = userService.deleteUser(deleteId);
        if(result){
            return ResultUtils.sucess(true);
        }
        return ResultUtils.sucess(false);
    }
}
