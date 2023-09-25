package com.example.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.result.ErrorCode;
import com.example.common.result.Result;
import com.example.common.result.ResultUtil;
import com.example.db.vo.user.LoginVO;
import com.example.db.vo.user.UserVO;
import com.example.web.utils.JwtUtils;
import com.example.db.domain.User;
import com.example.db.mapper.UserMapper;
import com.example.redis.RedisService;
import com.example.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.common.constant.RedisConstant.LOGIN_USER_KEY;

/**
* @author Chu
* @description 针对表【user】的数据库操作Service实现
* @createDate 2023-09-03 21:46:59
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    @Autowired
    private RedisService redisService;

    @Override
    public Result<LoginVO> login(String email, String password) {
        //查数据库
        User user = query().eq("email", email).eq("password",password).one();
        String token = JwtUtils.createToken(user);
        if(user == null) {
            return ResultUtil.fail(ErrorCode.NOT_FOUND_ERROR);
        }
        redisService.setCacheUser(LOGIN_USER_KEY + token, user);
        return ResultUtil.ok(new LoginVO(new UserVO(user), token));
    }

    @Override
    public Result deactivateUser(String email) {
        //todo 鉴权
//        redisService.getCacheObject()
        User user = query().eq("email", email).one();
        user.setStatus(1);
        save(user);
        return ResultUtil.ok(user);
    }
}




