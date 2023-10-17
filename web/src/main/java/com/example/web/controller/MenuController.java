package com.example.web.controller;

import com.example.common.result.Result;
import com.example.common.result.ResultUtil;
import com.example.api.vo.MenuVO;
import com.example.api.vo.user.UserVO;
import com.example.web.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    public Result<List<MenuVO>> list(UserVO user) {
        List<MenuVO> menuListByRole = menuService.getMenuListByRole(user.getRole());
        return ResultUtil.ok(menuListByRole);

    }
}
