package com.example.web.service;

import com.example.common.result.Result;
import com.example.db.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.db.vo.user.LoginVO;

/**
* @author Chu
* @description 针对表【user】的数据库操作Service
* @createDate 2023-09-03 21:46:59
*/
public interface UserService extends IService<User> {

    Result<LoginVO> login(String email, String password);

    Result deactivateUser(String email);
}
