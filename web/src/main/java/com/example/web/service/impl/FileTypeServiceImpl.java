package com.example.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.db.domain.FileType;
import com.example.web.service.FileTypeService;
import com.example.db.mapper.FileTypeMapper;
import org.springframework.stereotype.Service;

/**
* @author Chu
* @description 针对表【file_type】的数据库操作Service实现
* @createDate 2023-09-03 21:46:59
*/
@Service
public class FileTypeServiceImpl extends ServiceImpl<FileTypeMapper, FileType>
    implements FileTypeService{

}




