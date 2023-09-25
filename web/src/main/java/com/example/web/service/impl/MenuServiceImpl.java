package com.example.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.result.ResultUtil;
import com.example.db.domain.Menu;
import com.example.db.mapper.MenuMapper;
import com.example.db.vo.MenuVO;
import com.example.web.service.MenuService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
* @author Chu
* @description 针对表【menu】的数据库操作Service实现
* @createDate 2023-09-22 10:07:01
*/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
    implements MenuService {

    @Override
    public List<MenuVO> getMenuListByRole(Integer role) {
        //所有menu
        List<Menu> commonMenu =query().eq("role", role).list();
        //拿到爹转化为menuvo
        List<MenuVO> collect = commonMenu.stream().filter(menu -> menu.getParentId() == 0).map(menu -> BeanUtil.copyProperties(menu, MenuVO.class)).collect(Collectors.toList());
        collect.forEach(menuVO -> {
            //所有menu中 这个爹的儿子
            List<MenuVO> son = commonMenu.stream().filter(menu -> menu.getParentId().equals(menuVO.getId())).map(menu -> BeanUtil.copyProperties(menu, MenuVO.class)).collect(Collectors.toList());
            menuVO.setChildren(son);
        });
        return collect;

    }
}




