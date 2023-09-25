package com.example.web.controller;

import com.example.common.result.ErrorCode;
import com.example.common.result.Result;
import com.example.common.result.ResultUtil;
import com.example.db.domain.File;
import com.example.db.domain.FileType;
import com.example.db.dto.FileAddRequest;
import com.example.web.service.FileService;
import com.example.web.service.FileTypeService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

@RestController
@RequestMapping("/file")
public class FileController {

    @Resource
    private FileService fileService;

    @Resource
    private FileTypeService fileTypeService;

    @RequestMapping("/upLoad")
    public Result upLoad( MultipartFile file,  FileAddRequest fileAddRequest) {

        Integer filetypeId = fileAddRequest.getFiletypeId();

        String picPath = null;
        //判断文件是否为空
        if (!file.isEmpty()) {
            //获取选择的文件结构
            FileType fileType = fileTypeService.getById(1);
//            //定义上传目标路径,File.separator:自适应路径分隔符;
            String path = "D:\\02-Code\\demo10\\starter\\src\\main\\resources\\file\\";
            //获取原文件名
            String oldFileName = file.getOriginalFilename();
            //获取原文件名的后缀
            String prefix = FilenameUtils.getExtension(oldFileName);
            //todo userid
            //todo 判断上传文件格式和选择的文件类型是否相同
//            if (fileType.getName().equalsIgnoreCase(prefix)) {
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
                    return ResultUtil.fail(ErrorCode.OPERATION_ERROR);
                }
                picPath = path  + oldFileName;
                //然后把picPath路径值保存在数据库

//               File newFile = new File(null,oldFileName,picPath, UserHolder.getUser().getId(),filetypeId,fileAddRequest.getFilesize(),fileAddRequest.getVersion(),fileAddRequest.getNumber());
               File newFile = new File(null,oldFileName,picPath,1,filetypeId,fileAddRequest.getFilesize(),fileAddRequest.getVersion(),fileAddRequest.getNumber());
                fileService.save(newFile);
                //提交事务
                /*sqlSession.commit();*/
                return ResultUtil.ok(newFile);
//            } else {
//                return ResultUtil.fail(ErrorCode.OPERATION_ERROR, "上传文件格式和选择的文件类型不正确！");
//            }
        }else{
            return ResultUtil.fail(ErrorCode.OPERATION_ERROR, "上传文件不能为空");
        }
    }

    @RequestMapping("/downLoad")
    public Result<byte[]> downLoad(String filename) throws IOException {
        //下载文件的路径(这里绝对路径)
        String filepath= "D:\\02-Code\\School\\ssm-fileUpLoad\\src\\main\\webapp\\pic\\"+filename;
        java.io.File file =new java.io.File(filepath);
        //创建字节输入流，这里不实用Buffer类
        InputStream in = new FileInputStream(file);
        //available:获取输入流所读取的文件的最大字节数
        byte[] bytes = new byte[in.available()];
        //把字节读取到数组中
        in.read(bytes);
        //设置请求头
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Content-Disposition", "attchement;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
        in.close();
        //使用ResponseEntity作为controller的返回值，我们可以方便地处理响应的header，状态码以及body。
        return  ResultUtil.ok(bytes);
//        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

    @RequestMapping("/readImage")
    public void readImage(HttpServletResponse response, String pathName){
        System.out.println("pathName="+pathName);
        try {
            FileInputStream picture = new FileInputStream(pathName);
            int i = picture.available();
            byte[] data = new byte[i];
            picture.read(data);
            picture.close();
            response.setContentType("img/*");
            response.setCharacterEncoding("utf-8");
            ServletOutputStream out = response.getOutputStream();
            out.write(data);
            out.flush();
            out.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

//    @RequestMapping(value = "/getFileList",produces = {"application/json;charset=UTF-8"})
//    public String getFileList() {
//        List<File> fileList = fileService.list();
//
////        return JSON.toJSONStringWithDateFormat(fileList,"yyyy-MM-dd HH:mm:ss", SerializerFeature.DisableCircularReferenceDetect);
//    }
//
//    @RequestMapping(value = "/getFileType",produces = {"application/json;charset=UTF-8"})
//    @ResponseBody
//    private String getFileType(){
//        List<FileType> fileTypeList = fileTypeService.getFileType();
//        return JSON.toJSONString(fileTypeList);
//    }
//
//    @RequestMapping(value = "/getFileListByType",produces = {"application/json;charset=UTF-8"})
//    @ResponseBody
//    public String getFileListByType(int fid) {
//        List<FileMessage> fileList = fileMessageService.getFileByType(fid);
//        return JSON.toJSONStringWithDateFormat(fileList,"yyyy-MM-dd HH:mm:ss", SerializerFeature.DisableCircularReferenceDetect);
//    }

}
