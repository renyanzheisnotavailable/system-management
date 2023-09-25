package com.example.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.db.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Chu
* @description 针对表【user】的数据库操作Mapper
* @createDate 2023-09-03 21:46:59
* @Entity com.example.db.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




