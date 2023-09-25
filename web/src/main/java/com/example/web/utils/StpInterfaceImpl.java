package com.example.web.utils;

import cn.dev33.satoken.stp.StpInterface;
import com.example.db.vo.user.UserVO;
import com.example.web.service.UserService;
import org.apache.el.parser.Token;

import java.util.List;

public class StpInterfaceImpl implements StpInterface {

    private UserService userService;

    @Override
    public List<String> getPermissionList(Object o, String token) {
//        UserVO userVO = JwtUtils.verifyToken(token);
//        userService.query().eq(userVO.getId()).
        return null;
    }

    @Override
    public List<String> getRoleList(Object o, String token) {
        return null;
    }
}
