package com.example.web.service;

import com.example.db.domain.File;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
* @author Chu
* @description 针对表【file】的数据库操作Service
* @createDate 2023-09-03 21:46:59
*/
public interface FileService extends IService<File> {

    String updateAvatar( MultipartFile file);

}
