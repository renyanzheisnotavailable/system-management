package com.example.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.exception.BusinessException;
import com.example.common.exception.ErrorCode;
import com.example.common.result.ResultUtil;
import com.example.db.domain.File;

import com.example.db.domain.FileType;
import com.example.db.mapper.FileMapper;
import com.example.web.service.FileService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
* @author Chu
* @description 针对表【file】的数据库操作Service实现
* @createDate 2023-09-03 21:46:59
*/
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File>
    implements FileService {

    @Override
    public String updateAvatar(MultipartFile file) {

        String picPath = null;
        //判断文件是否为空
        if (!file.isEmpty()) {
            //定义上传目标路径,File.separator:自适应路径分隔符;
            String path = "D:\\02-Code\\demo10\\starter\\src\\main\\resources\\avatar\\";
            //获取原文件名
            String oldFileName = file.getOriginalFilename();
            //获取原文件名的后缀
            String prefix = FilenameUtils.getExtension(oldFileName);

            java.io.File targetFile = new java.io.File(path,oldFileName); //新建文件对象
            //判断是否有此文件
            if (!targetFile.exists()) {
                //文件不存在就创建
                targetFile.mkdirs();
            }
            try {
                //接收用户上传文件流,输出到指定文件里去
                file.transferTo(targetFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            picPath = path  + oldFileName;
            return picPath;
        }else{
            throw new BusinessException(40001, "图片为空");
        }
    }
}




