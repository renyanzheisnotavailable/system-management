package com.example.db.mapper;

import com.example.db.domain.File;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Chu
* @description 针对表【file】的数据库操作Mapper
* @createDate 2023-09-03 21:46:59
* @Entity com.example.db.domain.File
*/
@Mapper

public interface FileMapper extends BaseMapper<File> {

}




