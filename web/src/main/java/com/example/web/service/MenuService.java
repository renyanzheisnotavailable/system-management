package com.example.web.service;

import com.example.db.domain.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.api.vo.MenuVO;

import java.util.List;

/**
* @author Chu
* @description 针对表【menu】的数据库操作Service
* @createDate 2023-09-22 10:07:01
*/
public interface MenuService extends IService<Menu> {

    List<MenuVO> getMenuListByRole(Integer role);

}
