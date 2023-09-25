package com.example.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.db.domain.File;

import com.example.db.mapper.FileMapper;
import com.example.web.service.FileService;
import org.springframework.stereotype.Service;

/**
* @author Chu
* @description 针对表【file】的数据库操作Service实现
* @createDate 2023-09-03 21:46:59
*/
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File>
    implements FileService {

}




